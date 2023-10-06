package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.firstore.model.Room
import com.example.chatapp.ui.Constants
import com.example.chatapp.ui.addroom.AddRoomActivity
import com.example.chatapp.ui.chat.ChatActivity

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

            HomeViewEvent.navigateToDetailsRoom -> {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)


            }

            else -> {}
        }
    }

    private fun initObserves() {
        viewModel.events.observe(this, ::handleEvents)
        viewModel.rooms.observe(this, {
            adapter.bindList(it.toMutableList<Room>())

        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()

    }

    lateinit var adapter: RoomsRecyclerViewAdapter
    private fun initView() {
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
        adapter = RoomsRecyclerViewAdapter(null)
        viewBinding.contentHome.recycler.adapter = adapter
        adapter.onitemlistener = RoomsRecyclerViewAdapter.OnItemListener({ i: Int, room: Room ->
            navigateToRoom(room)
        })

        //how to prevent memory leak in data binding
    }

    private fun navigateToRoom(room: Room) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM, room)
        startActivity(intent)
    }
}