package com.example.jetpackcompose2.model

data class Role(
    val name: String,
    val description: String,
    val permissions: List<Permission>
)
