package com.example.jetpackcompose2.dto.response


data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val authenticated: Boolean
)
