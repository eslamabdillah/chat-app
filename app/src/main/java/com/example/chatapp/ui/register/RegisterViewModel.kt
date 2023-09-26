package com.example.chatapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.SessionProvider
import com.example.chatapp.commonclasses.SingleLiveEvent
import com.example.chatapp.firstore.UsersDao
import com.example.chatapp.firstore.model.User
import com.example.chatapp.ui.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {
    var messageLiveData = SingleLiveEvent<Message>()
    val userName = MutableLiveData<String>("eslam")
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmError = MutableLiveData<String?>()

    val eventLiveData = SingleLiveEvent<RegisterViewEvents>()

    //val auth=FirebaseAuth.getInstance()
    val auth = Firebase.auth
    fun register() {
        if (validForm()) {
            isLoading.value = true
            auth.createUserWithEmailAndPassword(
                email?.value!!,
                password?.value!!

            ).addOnCompleteListener({ task ->
                if (task.isSuccessful) {
                    insertUserToFirestore(task.result.user?.uid)
//                    messageLiveData.postValue(ViewError(message = task.result.user?.uid))
                } else {
                    isLoading.value = false
                    messageLiveData.postValue(Message(message = task.exception?.localizedMessage))
                }
            })
        }


    }

    private fun insertUserToFirestore(uid: String?) {
        val user = User(
            id = uid,
            userName = userName.value,
            email = email.value
        )
        UsersDao.createUser(user, { task ->
            isLoading.value = false
            if (task.isSuccessful) {
                messageLiveData.postValue(Message(
                    message = "user Register Successful",
                    posActionName = "ok",
                    onPosActionClick = {
                        //save user
                        SessionProvider.user = user
                        //navigate to home
                        eventLiveData.postValue(RegisterViewEvents.NavigateToHome)

                    }
                ))
            } else {
                messageLiveData.postValue(Message(message = task.exception?.localizedMessage))

            }

        })

    }

    private fun validForm(): Boolean {
        var isValid = true
        if (userName.value.isNullOrBlank()) {
            //show error
            userNameError.postValue("please enter your name")
            isValid = false
        } else {
            userNameError.postValue(null)
        }
        if (email.value.isNullOrBlank()) {
            //show error
            emailError.postValue("please enter your email")
            isValid = false

        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            //show error
            passwordError.postValue("please enter your password")
            isValid = false

        } else {
            passwordError.postValue(null)
        }
        if (passwordConfirmation.value.isNullOrBlank() ||
            passwordConfirmation.value != password.value
        ) {
            //show error
            passwordConfirmError.postValue("passwords does't match")
            isValid = false

        } else {
            passwordConfirmError.postValue(null)
        }
        return isValid
    }

    fun navigateToLogin() {
        eventLiveData.postValue(RegisterViewEvents.NavigateToLogin)
    }
}