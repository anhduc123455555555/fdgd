package com.example.jetpackcompose2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose2.Repository.ExerciseRepository
import com.example.jetpackcompose2.controller.ExerciseController
import com.example.jetpackcompose2.controller.GenericViewModelFactory
import com.example.jetpackcompose2.dto.response.ExerciseResponse

@Composable
fun ExerciseScreen(
    exerciseRepository: ExerciseRepository
) {
    val exerciseController: ExerciseController = viewModel(
        factory = GenericViewModelFactory { ExerciseController(exerciseRepository) }
    )

    val exercises by exerciseController.exerciseList.collectAsState()
    val message by exerciseController.message.collectAsState()

    // State cho form thêm/cập nhật bài tập
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var videoUrl by remember { mutableStateOf("") }
    var targetGoal by remember { mutableStateOf("") }
    var editingIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Quản lý bài tập", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        // Form nhập liệu
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tên bài tập") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Mô tả") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = videoUrl,
            onValueChange = { videoUrl = it },
            label = { Text("Link video") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = targetGoal,
            onValueChange = { targetGoal = it },
            label = { Text("Mục tiêu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nút tạo hoặc cập nhật
        Button(
            onClick = {
                val exercise = ExerciseResponse(name, description, videoUrl, targetGoal)
                if (editingIndex == null) {
                    exerciseController.createExercise(exercise)
                } else {
                    exerciseController.updateExercise(exercise)
                    editingIndex = null
                }
                // Reset form
                name = ""
                description = ""
                videoUrl = ""
                targetGoal = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (editingIndex == null) "Tạo mới bài tập" else "Cập nhật bài tập")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { exerciseController.getAllExercises() }, modifier = Modifier.fillMaxWidth()) {
            Text("Tải danh sách bài tập")
        }

        Spacer(modifier = Modifier.height(12.dp))

        message?.let {
            Text(text = it, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseItem(
                    exercise,
                    onEdit = {
                        name = it.name
                        description = it.description
                        videoUrl = it.videoUrl
                        targetGoal = it.targetGoal
                        editingIndex = exercises.indexOf(it)
                    },
                    onDelete = {
                        exerciseController.deleteExercise(it.name)
                    }
                )
            }
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: ExerciseResponse,
    onEdit: (ExerciseResponse) -> Unit,
    onDelete: (ExerciseResponse) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = exercise.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Mục tiêu: ${exercise.targetGoal}", style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                Button(onClick = { onEdit(exercise) }, modifier = Modifier.padding(end = 8.dp)) {
                    Text("Sửa")
                }
                Button(onClick = { onDelete(exercise) }) {
                    Text("Xóa")
                }
            }
        }
    }
}
