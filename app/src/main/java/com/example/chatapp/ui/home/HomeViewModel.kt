package com.example.chatapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.commonclasses.SingleLiveEvent

class HomeViewModel : ViewModel() {
    val events = SingleLiveEvent<HomeViewEvent>()

    fun navigateToAddRoom() {
        events.postValue(HomeViewEvent.navigateToAddRoom)
    }
}