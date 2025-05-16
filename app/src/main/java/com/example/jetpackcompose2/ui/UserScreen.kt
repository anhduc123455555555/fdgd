package com.example.jetpackcompose2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose2.Repository.UserRepository
import com.example.jetpackcompose2.controller.GenericViewModelFactory
import com.example.jetpackcompose2.controller.UserController
import com.example.jetpackcompose2.dto.response.UserResponse

@Composable
fun UserScreen(userRepository: UserRepository) {
    val userController: UserController = viewModel(
        factory = GenericViewModelFactory { UserController(userRepository) }
    )

    val users by userController.usersState.collectAsState()
    val userInfo by userController.userInfoState
    val updateMsg by userController.updateState
    val searchUser by userController.searchUserState

    var userIdEdit by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    var searchPhone by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Quản lý người dùng", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = userIdEdit,
            onValueChange = { userIdEdit = it },
            label = { Text("User ID (để cập nhật)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên người dùng") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Số điện thoại") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Ngày sinh") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (userIdEdit.isNotBlank()) {
                    val user = UserResponse(userIdEdit, username, phone, dob)
                    userController.updateUser(userIdEdit, user)
                }
                // Reset form
                userIdEdit = ""
                username = ""
                phone = ""
                dob = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cập nhật người dùng")
        }

        updateMsg?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                color = if (msg.contains("thành công", ignoreCase = true)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchPhone,
            onValueChange = { searchPhone = it },
            label = { Text("Tìm user theo số điện thoại") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { userController.findUserByPhone(searchPhone) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tìm kiếm")
        }

        Spacer(modifier = Modifier.height(8.dp))

        searchUser?.let {
            Text("Kết quả tìm kiếm:", style = MaterialTheme.typography.titleMedium)
            Text("ID: ${it.id}, Tên: ${it.username}, SĐT: ${it.phone}, DOB: ${it.dob}", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { userController.loadUsers() }, modifier = Modifier.fillMaxWidth()) {
            Text("Tải danh sách người dùng")
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: UserResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "ID: ${user.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Tên: ${user.username}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "SĐT: ${user.phone}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Ngày sinh: ${user.dob}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
