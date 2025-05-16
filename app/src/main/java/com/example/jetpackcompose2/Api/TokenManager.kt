package com.example.jetpackcompose2.Api

import android.content.Context
import android.util.Log

object TokenManager {

    // Lưu token vào SharedPreferences
    fun saveTokens(context: Context, token: String, refreshToken: String) {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("access_token", token)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    // Lấy token từ SharedPreferences
    fun getToken(context: Context): String? {
        val token = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            .getString("access_token", null)
        Log.d("TokenManager", "Lấy token từ SharedPreferences: $token")
        return token
    }


    // Lấy refresh token từ SharedPreferences
    fun getRefreshToken(context: Context): String? {
        return context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            .getString("refresh_token", null)
    }
}
