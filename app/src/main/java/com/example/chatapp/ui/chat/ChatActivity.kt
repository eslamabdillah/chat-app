package com.example.chatapp.ui.chat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.firstore.model.Room
import com.example.chatapp.ui.Constants

class ChatActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityChatBinding
    val viewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initParams()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.toastLiveData.observe(this, object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@ChatActivity, value, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.newsMessagesLiveData.observe(this, {
            Log.d("messageSendToAdapter", it.toString())
            messageAdapter.addnewMessage(it)
            viewBinding.contentChat.messageRecycler.smoothScrollToPosition(messageAdapter.itemCount)
        })
    }

    private fun initParams() {
        val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.EXTRA_ROOM, Room::class.java)
        } else {
            intent.getParcelableExtra(Constants.EXTRA_ROOM) as Room?
        }
        Log.d("roomname", room?.title.toString())
        viewModel.changeRoom(room)

    }


    val messageAdapter = MessageAdapter(null)
    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
        viewBinding.contentChat.messageRecycler.adapter = messageAdapter
        (viewBinding.contentChat.messageRecycler.layoutManager as LinearLayoutManager).stackFromEnd =
            true

    }
}