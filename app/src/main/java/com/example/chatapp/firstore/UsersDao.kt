package com.example.chatapp.firstore

import com.example.chatapp.firstore.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UsersDao {
    fun getUsersCollection(): CollectionReference {
        val database = Firebase.firestore
        return database.collection(User.COLLECTION_USERS)
    }

    fun createUser(user: User, OnCompleteListener: OnCompleteListener<Void>) {

        val docRef = getUsersCollection().document(user.id.toString())
        docRef.set(user).addOnCompleteListener(OnCompleteListener)

    }

    fun getUser(uid: String?, OnCompleteListener: OnCompleteListener<DocumentSnapshot>) {
        getUsersCollection().document(uid ?: "")
            .get().addOnCompleteListener(OnCompleteListener)


    }
}