package com.example.chatapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.ui.register.RegisterActivity
import com.example.chatapp.ui.showMessage

class LoginActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObserves()
    }

    private fun initObserves() {
        viewModel.messageLiveData.observe(this, { it ->
            showMessage(message = it.message ?: "something is wrong",
                posActionName = "ok",
                postAction = { dialog, i ->
                    dialog.dismiss()

                }

            )
        })
    }

    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel

        viewBinding.loginContent.dontHaveAccount.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })

    }
}