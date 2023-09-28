package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.addroom.AddRoomActivity

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initView()
        initObserves()

    }

    private fun handleEvents(homeViewEvent: HomeViewEvent?) {
        when (homeViewEvent) {
            HomeViewEvent.navigateToAddRoom -> {
                val intent = Intent(this, AddRoomActivity::class.java)
                startActivity(intent)
            }

            else -> {}
        }
    }

    private fun initObserves() {
        viewModel.events.observe(this, ::handleEvents)

    }

    private fun initView() {
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
        //how to prevent memory leak in data binding
    }
}