package com.example.chatapp.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.SessionProvider
import com.example.chatapp.commonclasses.SingleLiveEvent
import com.example.chatapp.firstore.UsersDao
import com.example.chatapp.firstore.model.User
import com.example.chatapp.ui.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    var email = MutableLiveData<String>("eslam@gmail.com")
    var password = MutableLiveData<String>("123456")
    var auth = Firebase.auth
    var isLoading = MutableLiveData<Boolean>()
    var messageLiveData = SingleLiveEvent<Message>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    val events = SingleLiveEvent<LoginViewEvents>()

    fun loginClick() {
        if (validForm()) {
            isLoading.value = true
            auth.signInWithEmailAndPassword(
                email?.value!!,
                password?.value!!

            ).addOnCompleteListener({ task ->
                if (task.isSuccessful) {
                    getUserFromFireStore(task.result.user?.uid)
                } else {
                    isLoading.value = false

                    messageLiveData.postValue(Message(message = task.exception?.localizedMessage))
                }
            })
        }


    }

    private fun getUserFromFireStore(uid: String?) {
        UsersDao
            .getUser(uid, { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    // check recvie id
                    Log.d("Sessionuser0", uid.toString())
                    //take user from resuld
                    val user = task.result.toObject(User::class.java)
                    Log.d("Sessionuserid", task.result.id)
                    SessionProvider.user = task.result.toObject(User::class.java)
                    messageLiveData.postValue(
                        Message(
                            message = "loged is succfulty",
                            posActionName = "ok",
                            onPosActionClick = {
                                events.postValue(LoginViewEvents.NavigateToHome)

                            },
                            isCancelable = false

                        )
                    )

                } else {
                    messageLiveData.postValue(Message(message = task.exception?.localizedMessage))

                }

            })

    }


    private fun validForm(): Boolean {
        var isValid = true

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

        return isValid
    }


}