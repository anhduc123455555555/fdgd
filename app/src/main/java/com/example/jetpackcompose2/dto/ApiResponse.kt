package com.example.jetpackcompose2.dto

data class ApiResponse<T>(
    val code: Int = 1000,
    val message: String? = null,
    val result: T? = null
)
