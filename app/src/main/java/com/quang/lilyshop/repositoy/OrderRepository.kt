package com.quang.lilyshop.repositoy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.OrderStatus

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
    fun getUserOrders(onSuccess: (List<OrderModel>) -> Unit, onFailure: (DatabaseError) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val ordersRef = database.child("orders").orderByChild("userId").equalTo(userId)

        ordersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderList = mutableListOf<OrderModel>()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(OrderModel::class.java)
                    if (order != null) {
                        orderList.add(order)
                    }
                }
                onSuccess(orderList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error)
            }
        })
    }
    fun getOrderById(idOrder:String,onSuccess: (OrderModel) -> Unit, onFailure: (DatabaseError) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val ordersRef = database.child("orders").child(idOrder)

        ordersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val order = snapshot.getValue(OrderModel::class.java)
                if (order != null) {
                    onSuccess(order)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error)
            }
        })
    }
    fun cancelOrder(idOrder:String,onSuccess: () -> Unit, onFailure: (DatabaseError) -> Unit) {
        val orderRef = database.child("orders").child(idOrder)

        // Lấy thông tin đơn hàng và cập nhật trạng thái thành CANCELLED
        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Cập nhật trạng thái đơn hàng thành CANCELLED
                    orderRef.child("status").setValue(OrderStatus.CANCELLED)
                        .addOnSuccessListener {
                            onSuccess() // Gọi callback thành công
                        }
                        .addOnFailureListener { error ->
                            onFailure(DatabaseError.fromException(error)) // Gọi callback lỗi
                        }
                } else {
                    onFailure(DatabaseError.fromException(Exception("Order not found")))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}