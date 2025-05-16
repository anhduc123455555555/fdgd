package com.example.jetpackcompose2.Api

import android.content.Context
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import kotlinx.coroutines.runBlocking
import com.example.jetpackcompose2.Repository.AuthRepository

class TokenAuthenticator(
    private val context: Context,
    private val authRepository: AuthRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null // Tránh vòng lặp vô hạn

        val newAccessToken = runBlocking {
            authRepository.refreshAccessToken() // Lấy access token mới
        }

        return if (!newAccessToken.isNullOrEmpty()) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken") // Thêm access token mới vào request
                .build()
        } else null
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var current = response.priorResponse
        while (current != null) {
            count++
            current = current.priorResponse
        }
        return count
    }
}
