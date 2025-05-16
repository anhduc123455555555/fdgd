package com.example.jetpackcompose2.dto.request

data class CreateRequest(
    val username: String,
    val password: String,
    val phone: String,
    val dob: String // Format Date to String (yyyy-MM-dd)
)