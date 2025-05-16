package com.example.jetpackcompose2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose2.controller.UserController

enum class BottomBarScreen(val label: String, val icon: ImageVector) {
    Home("Trang chủ", Icons.Default.Home),
    Route("Lộ trình", Icons.Default.Map),
    Profile("Cá nhân", Icons.Default.Person)
}

@Composable
fun MainScreen(userController: UserController) {
    val userInfo by userController.userInfoState

    // Gọi API lấy thông tin user khi mở MainScreen
    LaunchedEffect(Unit) {
        userController.loadUserInfo()
    }

    var selectedScreen by remember { mutableStateOf(BottomBarScreen.Home) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomBarScreen.values().forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                BottomBarScreen.Home -> HomeScreen(searchQuery) { searchQuery = it }
                BottomBarScreen.Route -> RouteScreen()
                BottomBarScreen.Profile -> {
                    if (userInfo != null) {
                        ProfileScreen(
                            username = userInfo!!.username,
                            phone = userInfo!!.phone,
                            dob = userInfo!!.dob
                        )
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(searchQuery: TextFieldValue, onSearchChange: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            label = { Text("Tìm kiếm") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dữ liệu giả ví dụ
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFBBDEFB))
                        .clickable { /* xử lý click nếu cần */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, fontSize = 18.sp, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun RouteScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Lộ trình chưa có nội dung", fontSize = 20.sp)
    }
}

@Composable
fun ProfileScreen(username: String, phone: String, dob: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Thông tin cá nhân", style = MaterialTheme.typography.headlineSmall)

        InfoRow(label = "Username", value = username)
        InfoRow(label = "Số điện thoại", value = phone)
        InfoRow(label = "Ngày sinh", value = dob)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}
