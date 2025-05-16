package com.example.jetpackcompose2.model

data class User(
    val id: String,
    val username: String,
    val firstname: String,
    val lastname: String,
    val dob: String,
    val roles: List<Role>,
    val customerProfile: CustomerProfile?
)
