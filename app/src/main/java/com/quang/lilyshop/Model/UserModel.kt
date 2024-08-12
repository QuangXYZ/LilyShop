package com.quang.lilyshop.Model

class UserModel(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val gender: Boolean = true,
    val picUrl: String = "",
    var timestamp: Long = System.currentTimeMillis(),
) {

}