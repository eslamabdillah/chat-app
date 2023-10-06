package com.example.chatapp.firstore

import com.example.chatapp.firstore.model.Message
import com.google.android.gms.tasks.OnCompleteListener

object MessagesDao {
    fun getMessagesCollection(roomId: String) =
        RoomDao.getRoomsCollection()
            .document(roomId)
            .collection(Message.COLLECTION_NAME)


    fun sendMessage(message: Message, onCompleteListener: OnCompleteListener<Void>) {
        val messagedoc = getMessagesCollection(message.roomID ?: "").document()
        message.id = messagedoc.id
        messagedoc.set(message)
            .addOnCompleteListener(onCompleteListener)


    }

}