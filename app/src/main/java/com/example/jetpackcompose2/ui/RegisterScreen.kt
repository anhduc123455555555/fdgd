package com.example.jetpackcompose2.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
import com.example.jetpackcompose2.controller.AuthController as AuthViewModel
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

val SuccessGreen = Color(0xFF2E7D32) // Xanh thành công
val ErrorRed = Color(0xFFD32F2F)     // Đỏ lỗi

@Composable
fun RegisterScreen(viewModel: AuthViewModel,navController: NavController) {
    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val dob = remember { mutableStateOf("") }
    val registerState by viewModel.registerState.collectAsState()

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Đăng ký tài khoản",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value =phone.value,
                onValueChange = { phone.value = it },
                label = { Text("phone") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Bọc DatePickerDialog trong Box clickable để show dialog khi bấm
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        DatePickerDialog(
                            context,
                            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                                dob.value = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth)
                            },
                            year, month, day
                        ).show()
                    }
            ) {
                OutlinedTextField(
                    value = dob.value,
                    onValueChange = {},
                    label = { Text("Ngày sinh") },
                    readOnly = true,
                    enabled = false,  // tắt bàn phím
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    viewModel.register(
                        username.value,
                        password.value,
                        phone.value,
                        dob.value
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đăng ký")
            }

            registerState?.let {
                val color = if (it == "Đăng ký thành công") SuccessGreen else ErrorRed

                // Nếu thành công thì chuyển hướng sang login
                if (it == "Đăng ký thành công") {
                    // Dùng LaunchedEffect để không crash do recomposition
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                }

                Text(
                    text = it,
                    color = color,
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = "Đã có tài khoản? Đăng nhập tại đây",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true } // Xóa Register khỏi backstack
                        }
                    },
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}
