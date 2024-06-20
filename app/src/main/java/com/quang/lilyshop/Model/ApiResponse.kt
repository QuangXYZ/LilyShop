package com.quang.lilyshop.Model

data class ApiResponse<T>(
    val results: List<T>
)

