package com.example.jetpackcompose2.Repository

import android.content.Context
import com.example.jetpackcompose2.Api.ApiService
import com.example.jetpackcompose2.Api.TokenManager
import com.example.jetpackcompose2.Api.UserApi
import com.example.jetpackcompose2.dto.response.UserResponse
import retrofit2.Response

class UserRepository(
    private val context: Context,
    private val api: UserApi
) {

    private fun getAuthToken(): String? {
        return TokenManager.getToken(context)?.let { "Bearer $it" }
    }

    suspend fun fetchUserInfo(): UserResponse? {
        val token = getAuthToken() ?: return null

        val response = api.getUserInfo(token)
        return if (response.isSuccessful) {
            response.body()?.result
        } else null
    }

    suspend fun fetchUsers(): List<UserResponse>? {
        val token = getAuthToken() ?: return null

        val response = api.getUsers(token)
        return if (response.isSuccessful) {
            response.body()?.result
        } else null
    }

    suspend fun fetchUserById(userId: String): UserResponse? {
        val token = getAuthToken() ?: return null

        val response = api.getUserById(userId, token)
        return if (response.isSuccessful) {
            response.body()?.result
        } else null
    }

    suspend fun updateUser(userId: String, userRequest: UserResponse): UserResponse? {
        val token = getAuthToken() ?: return null

        val response = api.updateUser(userId, userRequest, token)
        return if (response.isSuccessful) {
            response.body()?.result
        } else null
    }

    suspend fun findUserByPhone(phone: String): UserResponse? {
        val token = getAuthToken() ?: return null

        val response = api.findUserByPhone(phone, token)
        return if (response.isSuccessful) {
            response.body()?.result
        } else null
    }
}
