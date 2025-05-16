package com.example.jetpackcompose2.Repository

import android.content.Context
import com.example.jetpackcompose2.Api.ApiService
import com.example.jetpackcompose2.Api.TokenManager
import com.example.jetpackcompose2.dto.response.ExerciseResponse
import com.example.jetpackcompose2.dto.ApiResponse
import retrofit2.Response

class ExerciseRepository(
    private val context: Context,
    private val api: ApiService,
    private val tokenManager: TokenManager  // để lấy token
) {

    private fun getToken(): String? {
        return tokenManager.getToken(context)
    }

    suspend fun createExercise(request: ExerciseResponse): ApiResponse<ExerciseResponse>? {
        val token = getToken() ?: return null
        val response = api.createExercise(request, "Bearer $token")
        return handleResponse(response)
    }

    suspend fun getAllExercises(): ApiResponse<List<ExerciseResponse>>? {
        val token = getToken() ?: return null
        val response = api.getAllExercises("Bearer $token")
        return handleResponse(response)
    }

    suspend fun getExerciseById(id: String): ApiResponse<ExerciseResponse>? {
        val token = getToken() ?: return null
        val response = api.getExerciseById(id, "Bearer $token")
        return handleResponse(response)
    }

    suspend fun updateExercise(request: ExerciseResponse): ApiResponse<ExerciseResponse>? {
        val token = getToken() ?: return null
        val response = api.updateExercise(request, "Bearer $token")
        return handleResponse(response)
    }

    suspend fun deleteExercise(id: String): ApiResponse<String>? {
        val token = getToken() ?: return null
        val response = api.deleteExercise(id, "Bearer $token")
        return handleResponse(response)
    }

    private fun <T> handleResponse(response: Response<ApiResponse<T>>): ApiResponse<T>? {
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Xử lý lỗi, ví dụ log hoặc trả về null
            null
        }
    }
}
