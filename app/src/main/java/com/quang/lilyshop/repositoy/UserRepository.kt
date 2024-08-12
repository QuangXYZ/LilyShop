package com.quang.lilyshop.repositoy

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val database : DatabaseReference) {

    fun checkUserExits(uid: String, callback: (Boolean) -> Unit) {
        database.child("users").child(uid).addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }

        })
    }

    fun createUser(user: User, onComplete: (Boolean) -> Unit) {
        database.child("users").child(user.uid).setValue(user)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }
}