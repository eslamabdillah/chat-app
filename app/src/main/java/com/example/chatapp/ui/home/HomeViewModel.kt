package com.example.chatapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.commonclasses.SingleLiveEvent
import com.example.chatapp.firstore.RoomDao
import com.example.chatapp.firstore.model.Room
import com.example.chatapp.ui.Message

class HomeViewModel : ViewModel() {
    val events = SingleLiveEvent<HomeViewEvent>()
    val rooms = MutableLiveData<List<Room>>()
    val messageLiveData = MutableLiveData<Message>()


    fun navigateToAddRoom() {
        events.postValue(HomeViewEvent.navigateToAddRoom)
    }

    fun loadRooms() {
        RoomDao.getAllRooms() { task ->
            if (task.isSuccessful) {
                rooms.postValue(task.result.toObjects(Room::class.java).toMutableList<Room>())
            } else {
                messageLiveData.postValue(Message(
                    message = "error happen when load rooms",
                    posActionName = "try again",
                    onPosActionClick = {
                        loadRooms()
                    },
                    negActionName = "canel",
                    onNegActionClick = {
                    }
                ))
            }
        }
    }


}