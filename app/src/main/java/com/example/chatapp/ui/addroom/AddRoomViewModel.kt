package com.example.chatapp.ui.addroom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.SessionProvider
import com.example.chatapp.commonclasses.SingleLiveEvent
import com.example.chatapp.firstore.RoomDao
import com.example.chatapp.firstore.model.Category
import com.example.chatapp.ui.Message
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class AddRoomViewModel : ViewModel() {
    val categories = Category.getCategories()
    val roomName = MutableLiveData<String>()
    val roomNameError = MutableLiveData<String?>()
    val roomDesc = MutableLiveData<String>()
    val roomDescError = MutableLiveData<String?>()
    private var selectCategory = categories[0]
    val messageLiveData = SingleLiveEvent<Message>()
    val events = SingleLiveEvent<AddRoomEvents>()

    private fun validForm(): Boolean {
        var isValid = true
        if (roomName.value.isNullOrBlank()) {
            roomNameError.postValue("please enter room title")
            isValid = false
        } else {
            roomNameError.postValue(null)
        }
        if (roomDesc.value.isNullOrBlank()) {
            roomDescError.postValue("please describe the room")
            isValid = false
        } else {
            roomDescError.postValue(null)
        }
        return isValid
    }

    fun createRoom() {
        Log.d("room", "clicked")
        if (!validForm()) return

        RoomDao.createRoom(
            title = roomName.value!!,
            desc = roomDesc.value!!,
            categoryId = selectCategory.id,
            ownerId = SessionProvider.user?.id!!,
            onCompleteListener = object : OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    if (task.isSuccessful) {
                        Log.d("room", "done")
                        //message
                        messageLiveData.postValue(Message(
                            message = "room created",
                            posActionName = "ok",
                            onPosActionClick = {
                                events.postValue(AddRoomEvents.navigateToHome)
                            }
                        ))
                        //navigate to home
                    } else {
                        messageLiveData.postValue(
                            Message(
                                message = "something went wrong ",
                                posActionName = "ok",


                                )
                        )

                    }
                }
            }

        )


    }

    fun onCategorySelect(position: Int) {
        selectCategory = categories[position]
    }

}