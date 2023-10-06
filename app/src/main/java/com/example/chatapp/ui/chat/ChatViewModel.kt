package com.example.chatapp.ui.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.SessionProvider
import com.example.chatapp.commonclasses.SingleLiveEvent
import com.example.chatapp.firstore.MessagesDao
import com.example.chatapp.firstore.model.Message
import com.example.chatapp.firstore.model.Room
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener

class ChatViewModel : ViewModel() {
    private var room: Room? = null
    var messageLiveData = MutableLiveData<String>()
    val toastLiveData = SingleLiveEvent<String>()
    val newsMessagesLiveData = SingleLiveEvent<MutableList<Message>>()


    fun sendMessage() {
        if (messageLiveData.value.isNullOrBlank()) return
        val message = Message(
            content = messageLiveData.value,
            datetime = Timestamp.now(),
            senderId = SessionProvider.user?.id,
            senderName = SessionProvider.user?.userName,
            roomID = room?.id
        )
        MessagesDao.sendMessage(message) { task ->
            if (task.isSuccessful) {
                messageLiveData.value = ""
                return@sendMessage

            }
            toastLiveData.value = "something is wrong , please try again later "


        }


    }

    fun changeRoom(room: Room?) {
        this.room = room
        trylistenForMessageInRoom()
    }

    private fun trylistenForMessageInRoom() {

        MessagesDao.getMessagesCollection(room?.id ?: "")
            .orderBy("datetime")
            .limit(100)
            .addSnapshotListener(EventListener({ snapShot, error ->
                val newMessage = mutableListOf<Message>()
                snapShot?.documentChanges?.forEach({ docChange ->
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val addMessage = docChange.document.toObject(Message::class.java)
                        newMessage.add(addMessage)

                    }
                    newsMessagesLiveData.postValue(newMessage)


                })
            }))

        Log.d("newsMessagesLiveData", newsMessagesLiveData.toString())
    }

//    fun listenForMessageInRoom(){
//        MessagesDao.getMessagesCollection(room?.id?:"")
//            .orderBy("datetime")
//            .limit(100)
//            .addSnapshotListener(EventListener({
//                    snapshot, error ->
////                snapshot?.documents?.forEach({doc->
////                    val messages=doc.toObject(Message::class.java)
////                }
////                )
//
//                val newsMessage= mutableListOf<Message>()
//                snapshot?.documentChanges?.forEach({docChange->
//                    if (docChange.type== DocumentChange.Type.ADDED) {
//                        val message=docChange.document.toObject(Message::class.java)
//                         newsMessage.add(message)
//
//
//                    }
//
////                    if (docChange.type== DocumentChange.Type.ADDED){
////                        val message=docChange.document.toObject(Message::class.java)
////                        newsMessage.add((message))
////
////                    }
//                    if (docChange.type== DocumentChange.Type.MODIFIED){
//
//                    }
//                    if (docChange.type== DocumentChange.Type.REMOVED){
//
//                    }
//
//                })
//
//
//                Log.d("newMessageWhenEnterRoom",newsMessage.toString())
//
//                newsMessagesLiveData.postValue(newsMessage)
//                Log.d("newsMessagesLiveData",newsMessage.toString())
//
//
//
//
//            }))

}

