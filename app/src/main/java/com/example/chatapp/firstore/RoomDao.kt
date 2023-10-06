package com.example.chatapp.firstore

import com.example.chatapp.firstore.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
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


        val room = Room(
            id = docRef.id,
            title = title,
            description = desc,
            ownerId = ownerId,
            categoryId = categoryId
        )
        docRef.set(room).addOnCompleteListener(onCompleteListener)
    }

//    db.collection("cities")
//    .get()
//    .addOnSuccessListener { result ->
//        for (document in result) {
//            Log.d(TAG, "${document.id} => ${document.data}")
//        }
//    }
//    .addOnFailureListener { exception ->
//        Log.d(TAG, "Error getting documents: ", exception)
//    }

    fun getAllRooms(onCompleteListener: OnCompleteListener<QuerySnapshot>) {
        Firebase.firestore.collection("rooms").get().addOnCompleteListener(onCompleteListener)
    }

}