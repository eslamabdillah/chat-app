package com.example.chatapp.firstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val categoryId: Int? = null,
    val ownerId: String? = null
) : Parcelable {
    companion object {
        const val COLLECTION_NAME = "rooms"
    }
}
