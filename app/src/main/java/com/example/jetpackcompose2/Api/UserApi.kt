package com.example.jetpackcompose2.Api

import com.example.jetpackcompose2.dto.ApiResponse
import com.example.jetpackcompose2.dto.request.CreateRequest
import com.example.jetpackcompose2.dto.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("/user/lay")
    suspend fun getUsers(
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<List<UserResponse>>>

    @GET("/user/laythongtin")
    suspend fun getUserInfo(
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<UserResponse>>

    @GET("/user/{userid}")
    suspend fun getUserById(
        @Path("userid") userId: String,
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<UserResponse>>

    @PUT("/user/{userid}")
    suspend fun updateUser(
        @Path("userid") userId: String,
        @Body request: UserResponse,
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<UserResponse>>

    @GET("/user/timkiem-sdt")
    suspend fun findUserByPhone(
        @Query("phone") phone: String,
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<UserResponse>>
}
