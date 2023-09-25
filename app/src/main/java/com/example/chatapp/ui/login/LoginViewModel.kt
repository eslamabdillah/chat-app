package com.example.chatapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.ViewError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var auth = Firebase.auth
    var isLoading = MutableLiveData<Boolean>()
    var messageLiveData = MutableLiveData<ViewError>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    fun loginClick() {
        if (validForm()) {
            isLoading.value = true
            auth.signInWithEmailAndPassword(
                email?.value!!,
                password?.value!!

            ).addOnCompleteListener({ task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    messageLiveData.postValue(ViewError(message = task.result.user?.uid))
                } else {
                    messageLiveData.postValue(ViewError(message = task.exception?.localizedMessage))
                }
            })
        }


    }

//    fun loginClick(){
//        isloadingLiveData.postValue(false)
//        if(validForm()){
//            auth.signInWithEmailAndPassword(emailLiveData.value!!,passwordLiveData.value!!)
//                .addOnCompleteListener({task->
//                    isloadingLiveData.postValue(false)
//                    if (task.isSuccessful){
//                        //show masseage
//                        messageLiveData.postValue(ViewError(message = task.result.user?.uid))
//
//                    }
//                    else{
//                        //show message
//                        messageLiveData.postValue(ViewError(message = task.exception?.localizedMessage))
//                    }
//
//                })
//        }
//
//
//    }

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