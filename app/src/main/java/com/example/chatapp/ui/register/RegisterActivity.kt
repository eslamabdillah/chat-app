package com.example.chatapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.Message
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity
import com.example.chatapp.ui.showMessage

class RegisterActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObserves()


//        viewBinding=ActivityRegisterBinding.inflate(layoutInflater)
//        setContentView(viewBinding.root)
    }

    private fun initObserves() {
        viewModel.messageLiveData.observe(this, object : Observer<Message> {
            override fun onChanged(message: Message) {
                showMessage(
                    message = message.message ?: "something is wrong",
                    posActionName = "ok",
                    postAction = message.onPosActionClick,
                    negActionName = message.negActionName,
                    negAction = message.onNegActionClick,
                    isCancelable = message.isCancelable


                )
            }
        })

        //using pointer to function
        viewModel.eventLiveData.observe(this, ::handleEvents)
    }

    private fun handleEvents(registerViewEvents: RegisterViewEvents?) {
        when (registerViewEvents) {
            RegisterViewEvents.NavigateToHome -> {
                navigateToHome()

            }

            RegisterViewEvents.NavigateToLogin -> {
                navigateToLogin()


            }

            else -> {}
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel

//        viewBinding.content.haveAccount.setOnClickListener({
//            var intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        })
    }
}