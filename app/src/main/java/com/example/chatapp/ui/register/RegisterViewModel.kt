package com.example.chatapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    val userName = MutableLiveData<String>("eslam")
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmError = MutableLiveData<String?>()


    fun register() {
        if (validForm()) return

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
}