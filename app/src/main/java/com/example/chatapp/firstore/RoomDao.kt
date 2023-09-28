package com.example.chatapp.firstore

import android.util.Log
import com.example.chatapp.firstore.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object RoomDao {
    fun getRoomsCollection(): CollectionReference {
        return Firebase.firestore
            .collection(Room.COLLECTION_NAME)
    }

    fun createRoom(
        title: String,
        desc: String,
        ownerId: String,
        categoryId: Int,
        onCompleteListener: OnCompleteListener<Void>
    ) {
        val collection = getRoomsCollection()
        val docRef = collection.document()
        Log.d("room+id", docRef.id)

        Log.d("room+title", title)
        Log.d("room+desc", desc)
        Log.d("room+ownerid", ownerId)
        Log.d("room+categoryid", categoryId.toString())

        val room = Room(
            id = docRef.id,
            title = title,
            description = desc,
            ownerId = ownerId,
            categoryId = categoryId
        )
        docRef.set(room).addOnCompleteListener(onCompleteListener)
    }
}