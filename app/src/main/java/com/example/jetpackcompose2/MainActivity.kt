package com.example.jetpackcompose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.jetpackcompose2.Api.ApiService
import com.example.jetpackcompose2.Api.TokenManager
import com.example.jetpackcompose2.Api.UserApi
import com.example.jetpackcompose2.Repository.AuthRepository
import com.example.jetpackcompose2.Repository.ExerciseRepository
import com.example.jetpackcompose2.Repository.UserRepository
import com.example.jetpackcompose2.controller.AuthController
import com.example.jetpackcompose2.controller.GenericViewModelFactory
import com.example.jetpackcompose2.controller.UserController
import com.example.jetpackcompose2.navigation.AppNavigation
import com.example.jetpackcompose2.ui.MyFitnessTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    // Retrofit chung
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API interface từ Retrofit
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    // TokenManager singleton hoặc instance (có thể bạn cần tạo hoặc import)
    private val tokenManager = TokenManager

    // Controllers với đúng repository & api
    private val authController: AuthController by viewModels {
        GenericViewModelFactory {
            AuthController(AuthRepository(applicationContext, apiService))
        }
    }

    private val userController: UserController by viewModels {
        GenericViewModelFactory {
            UserController(UserRepository(applicationContext, userApi))
        }
    }

    // Repositories đúng instance
    private val userRepository by lazy { UserRepository(applicationContext, userApi) }
    private val exerciseRepository by lazy { ExerciseRepository(applicationContext, apiService, tokenManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFitnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        authController = authController,
                        userController = userController,
                        userRepository = userRepository,
                        exerciseRepository = exerciseRepository
                    )
                }
            }
        }
    }
}
