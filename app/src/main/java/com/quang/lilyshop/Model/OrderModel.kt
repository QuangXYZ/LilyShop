package com.quang.lilyshop.Model

data class OrderModel(
    var id: String = "",
    var userId: String = "",
    var products: List<ProductModel> = listOf(),
    var shippingFee: Double = 0.0,
    var tax: Double = 0.0,
    var totalPrice: Double = 0.0,
    var timestamp: Long = System.currentTimeMillis()
) {
    constructor() : this("", "", listOf(), 0.0,0.0,0.0, System.currentTimeMillis())
}