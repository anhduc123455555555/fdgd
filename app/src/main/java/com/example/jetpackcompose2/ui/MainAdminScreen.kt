package com.example.jetpackcompose2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.jetpackcompose2.Repository.ExerciseRepository
import com.example.jetpackcompose2.Repository.UserRepository

@Composable
fun MainAdminScreen(
    userRepository: UserRepository,
    exerciseRepository: ExerciseRepository
) {
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Bài tập", "Người dùng")

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> ExerciseScreen(exerciseRepository)
                1 -> UserScreen(userRepository)
            }
        }
    }
}
