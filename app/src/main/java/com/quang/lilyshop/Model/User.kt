package com.quang.lilyshop.Model

data class User(
    val uid: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val photoUrl: String = "",
    val gender: Boolean = true
)