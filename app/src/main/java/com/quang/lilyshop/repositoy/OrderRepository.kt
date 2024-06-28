package com.quang.lilyshop.repositoy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.quang.lilyshop.Model.OrderModel

class OrderRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    fun saveOrder(order: OrderModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val orderRef = database.child("orders").push()

        order.id = orderRef.key ?: ""
        order.userId = userId

        orderRef.setValue(order)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

}