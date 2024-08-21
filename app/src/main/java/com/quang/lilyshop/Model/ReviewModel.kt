package com.quang.lilyshop.Model

data class ReviewModel(
    var productId: String = "",
    var userId: String = "",
    var userName: String = "",

    var rating: Float = 0.0f,
    var comment: String = "",
    var timestamp: Long = System.currentTimeMillis()
)
{


}