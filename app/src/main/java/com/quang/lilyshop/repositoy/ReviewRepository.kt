package com.quang.lilyshop.repositoy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Model.OrderStatus
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel

class ReviewRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    fun saveReview(review: ReviewModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid?: return
        val reviewRef = database.child("reviews").child(review.productId).child(userId)
        review.userId = userId


        reviewRef.setValue(review)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }


    fun getReviewsByProduct(productId: String, onSuccess: (List<ReviewModel>) -> Unit, onFailure: (DatabaseError) -> Unit) {
        val reviewsRef = database.child("reviews").orderByChild("productId").equalTo(productId)

        reviewsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reviewList = mutableListOf<ReviewModel>()
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(ReviewModel::class.java)
                    if (review != null) {
                        reviewList.add(review)
                    }
                }
                onSuccess(reviewList)
            }
            override fun onCancelled(error: DatabaseError) {
                onFailure(error)
            }
        })
    }

    fun getProductForReview( onSuccess: (List<ProductModel>) -> Unit, onFailure: (DatabaseError) -> Unit) {
        val userId = auth.currentUser?.uid?: return
        val ordersRef = FirebaseDatabase.getInstance().getReference("orders")
        val reviewsRef = FirebaseDatabase.getInstance().getReference("reviews")
        var pendingCallbacks = 0
        val productIdsInReviewList = HashSet<String>()


        ordersRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productsForReview = mutableListOf<ProductModel>()

                for (orderSnapshot in snapshot.children) {
                    val orderStatus = orderSnapshot.child("status").getValue(OrderStatus::class.java)
                    if (orderStatus == OrderStatus.DELIVERED) {
                        for (productSnapshot in orderSnapshot.child("products").children) {
                            pendingCallbacks ++
                            val product = productSnapshot.getValue(ProductModel::class.java)
                            val productId = product?.id ?: continue
                            reviewsRef.child(productId).child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(reviewSnapshot: DataSnapshot) {
                                    if (!reviewSnapshot.exists()) { // Sản phẩm chưa được đánh giá
                                        if (!productIdsInReviewList.contains(product.id)) {
                                            productsForReview.add(product)
                                            productIdsInReviewList.add(product.id) // Lưu id sản phẩm để tránh trùng lặp khi lấy sản phẩm đã đánh giá
                                        }
                                    }
                                    pendingCallbacks --

                                    if (pendingCallbacks == 0) onSuccess(productsForReview)


                                }

                                override fun onCancelled(error: DatabaseError) {
                                    pendingCallbacks --
                                    onFailure(error)
                                }
                            })
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error)
            }
        })
    }
}