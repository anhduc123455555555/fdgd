package com.example.jetpackcompose2.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose2.controller.AuthController
import com.example.jetpackcompose2.controller.UserController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(authController: AuthController, userController: UserController, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val loginState = authController.loginState.value
    val userInfo = userController.userInfoState.value


    // Check login state


    val successColor = Color(0xFF2E7D32)
    val errorColor = Color(0xFFD32F2F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Đăng nhập",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = errorColor,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                when {
                    username.trim().isEmpty() -> errorMessage = "Username cannot be empty"
                    password.trim().isEmpty() -> errorMessage = "Password cannot be empty"
                    else -> {
                        errorMessage = null
                        authController.login(username.trim(), password.trim())
                    }
                }
            },
            enabled = loginState != "Loading...",
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Đăng nhập",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Chưa có tài khoản? Đăng ký tại đây",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = true }
                    }
                },
            style = MaterialTheme.typography.bodyMedium
        )

        when {
            loginState == "Loading..." -> CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp
            )
            loginState != null -> {
                val color = if (loginState.contains("thành công", ignoreCase = true)) successColor else errorColor
                Text(
                    text = loginState,
                    color = color,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

//    LaunchedEffect(loginState) {
//        println("Login state: $loginState")
//        if (loginState == "Đăng nhập thành công") {
//            userController.userInfoState
//        }
//    }
//
//    // Check user info and navigate
//    LaunchedEffect(userInfo) {
//        println("User info: $userInfo")
//        if (userInfo != null) {
//            navController.navigate("main") {
//                popUpTo("login") { inclusive = true }
//            }
//        }
//    }

    LaunchedEffect(userInfo) {
        println("User info: $userInfo")
        if (userInfo != null) {
            if (username == "admin" && password == "admin") {
                navController.navigate("mainAdmin") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

}

