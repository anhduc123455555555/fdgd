package com.example.jetpackcompose2.Repository

import android.content.Context
import android.util.Log
import com.example.jetpackcompose2.Api.ApiService
import com.example.jetpackcompose2.Api.RetrofitClient
import com.example.jetpackcompose2.Api.TokenManager
import com.example.jetpackcompose2.dto.request.CreateRequest
import com.example.jetpackcompose2.dto.request.LoginRequest
import com.example.jetpackcompose2.dto.request.RefreshRequest

class AuthRepository(
    private val context: Context,
    private val api: ApiService
) {
    suspend fun register(createRequest: CreateRequest): Boolean {
        Log.d("AuthRepository", "Đang gửi yêu cầu đăng ký với tài khoản: ${createRequest.username}")

        // Gửi yêu cầu đăng ký
        val response = api.create(createRequest)

        return if (response.isSuccessful) {
            val apiResponse = response.body()

            // Kiểm tra mã code trong apiResponse
            if (apiResponse?.code == 0) {
                Log.d("AuthRepository", "Đăng ký thành công")
                true
            } else {
                Log.e("AuthRepository", "Đăng ký thất bại: ${apiResponse?.message}")
                false
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("AuthRepository", "Đăng ký thất bại - Nội dung lỗi từ server: $errorBody")
            false
        }
    }


    suspend fun login(username: String, password: String): Boolean {
        Log.d("AuthRepository", "Đang gửi yêu cầu đăng nhập với tài khoản: $username")

        val request = LoginRequest(username, password)
        val response = api.login(request)

        return if (response.isSuccessful) {
            val apiResponse = response.body()
            val result = apiResponse?.result

            if (result != null && result.authenticated) {
                Log.d("AuthRepository", "Đăng nhập thành công - Token: ${result.token}, RefreshToken: ${result.refreshToken}")
                TokenManager.saveTokens(context, result.token ?: "", result.refreshToken ?: "")
                val savedToken = TokenManager.getToken(context)
                Log.d("AuthRepository", "Token đã lưu: $savedToken")

                RetrofitClient.resetInstance()
                true
            } else {
                Log.e("AuthRepository", "Đăng nhập thất bại - authenticated = false hoặc thiếu dữ liệu")
                false
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("AuthRepository", "Đăng nhập thất bại - Nội dung lỗi từ server: $errorBody")
            false
        }
    }

    suspend fun refreshAccessToken(): String? {

        // lấy refreshtoken đã lưu trong tokenmanager
        val refreshToken = TokenManager.getRefreshToken(context)
            // kiểm tra xem nó có null không
        if (refreshToken.isNullOrEmpty()) return null

        val response = api.refreshToken(RefreshRequest(refreshToken)) // Response<ApiResponse<RefreshResponse>>

        return if (response.isSuccessful) {
            val result = response.body()?.result  // result: RefreshResponse?
            val newAccessToken = result?.accessToken
            if (!newAccessToken.isNullOrEmpty()) {
                TokenManager.saveTokens(context, newAccessToken, refreshToken)
            }
            newAccessToken
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("AuthRepository", "Lỗi refresh token: $errorBody")
            null
        }
    }



}




