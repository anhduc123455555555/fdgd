package com.example.jetpackcompose2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose2.Repository.ExerciseRepository
import com.example.jetpackcompose2.Repository.UserRepository
import com.example.jetpackcompose2.ui.LoginScreen
import com.example.jetpackcompose2.ui.RegisterScreen
import com.example.jetpackcompose2.controller.AuthController
import com.example.jetpackcompose2.controller.UserController
import com.example.jetpackcompose2.ui.ExerciseScreen
import com.example.jetpackcompose2.ui.MainAdminScreen
import com.example.jetpackcompose2.ui.MainScreen
import com.example.jetpackcompose2.ui.UserScreen


@Composable
fun AppNavigation(
    authController: AuthController,
    userController: UserController,
    userRepository: UserRepository,
    exerciseRepository: ExerciseRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(authController, userController, navController)
        }
        composable("register") {
            RegisterScreen(authController, navController)
        }
        composable("main") {
            MainScreen(userController)
        }
        composable("main_admin") {
            MainAdminScreen(userRepository, exerciseRepository)
        }
        composable("user") {
            UserScreen(userRepository)
        }
        composable("exercise") {
            ExerciseScreen(exerciseRepository)
        }
    }
}


