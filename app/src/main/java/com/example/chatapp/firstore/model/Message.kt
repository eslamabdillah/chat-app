package com.example.chatapp.firstore.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Message(
    var id: String? = null,
    var content: String? = null,
    val datetime: Timestamp? = null,
    val senderName: String? = null,
    var senderId: String? = null,
    val roomID: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "messages"

    }

    fun formatTime(): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(datetime?.toDate())

    }
}