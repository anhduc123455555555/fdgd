package com.example.jetpackcompose2.Api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Lấy token từ SharedPreferences hoặc nơi lưu token
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("access_token", null)

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
