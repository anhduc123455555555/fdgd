    package com.example.jetpackcompose2.Api

    import com.example.jetpackcompose2.dto.ApiResponse
    import com.example.jetpackcompose2.dto.request.CreateRequest
    import com.example.jetpackcompose2.dto.request.LoginRequest
    import com.example.jetpackcompose2.dto.request.RefreshRequest
    import com.example.jetpackcompose2.dto.response.ExerciseResponse
    import com.example.jetpackcompose2.dto.response.LoginResponse
    import com.example.jetpackcompose2.dto.response.RefreshResponse
    import com.example.jetpackcompose2.dto.response.UserResponse
    import retrofit2.Response
    import retrofit2.http.Body
    import retrofit2.http.GET
    import retrofit2.http.Header
    import retrofit2.http.POST

    interface ApiService {
        @POST("/auth/login")
        suspend fun login(@Body loginRequest: LoginRequest) : Response<ApiResponse<LoginResponse>>

        @POST("user/them")
        suspend fun create(@Body createRequest: CreateRequest) : Response<ApiResponse<Unit>>

        @POST("/auth/refresh")
        suspend fun refreshToken(@Body refreshRequest: RefreshRequest) : Response<ApiResponse<RefreshResponse>>

        @GET("/user/laythongtin")
        suspend fun getUserInfo(  @Header("Authorization") token: String): Response<ApiResponse<UserResponse>>

        @POST("/exercise/them")
        suspend fun createExercise(@Body request: ExerciseResponse,@Header("Authorization") token: String): Response<ApiResponse<ExerciseResponse>>

        @POST("/exercise/danhsach")
        suspend fun getAllExercises(@Header("Authorization") token: String): Response<ApiResponse<List<ExerciseResponse>>>

        @POST("/exercise/chitiet")
        suspend fun getExerciseById(@Body id: String,@Header("Authorization") token: String): Response<ApiResponse<ExerciseResponse>>

        @POST("/exercise/sua")
        suspend fun updateExercise(@Body request: ExerciseResponse,@Header("Authorization") token: String): Response<ApiResponse<ExerciseResponse>>

        @POST("/exercise/xoa")
        suspend fun deleteExercise(@Body id: String,@Header("Authorization") token: String): Response<ApiResponse<String>>

    }