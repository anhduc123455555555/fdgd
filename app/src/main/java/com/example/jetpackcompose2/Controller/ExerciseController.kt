package com.example.jetpackcompose2.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose2.Repository.ExerciseRepository
import com.example.jetpackcompose2.dto.response.ExerciseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseController(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _exerciseList = MutableStateFlow<List<ExerciseResponse>>(emptyList())
    val exerciseList: StateFlow<List<ExerciseResponse>> = _exerciseList

    private val _exerciseDetail = MutableStateFlow<ExerciseResponse?>(null)
    val exerciseDetail: StateFlow<ExerciseResponse?> = _exerciseDetail

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun createExercise(request: ExerciseResponse) {
        viewModelScope.launch {
            val result = exerciseRepository.createExercise(request)
            if (result != null && result.result != null) {
                _message.value = "Tạo bài tập thành công"
            } else {
                _message.value = "Tạo bài tập thất bại"
            }
        }
    }

    fun getAllExercises() {
        viewModelScope.launch {
            val result = exerciseRepository.getAllExercises()
            if (result != null && result.result != null) {
                _exerciseList.value = result.result
            } else {
                _message.value = "Lấy danh sách bài tập thất bại"
            }
        }
    }

    fun getExerciseById(id: String) {
        viewModelScope.launch {
            val result = exerciseRepository.getExerciseById(id)
            if (result != null && result.result != null) {
                _exerciseDetail.value = result.result
            } else {
                _message.value = "Lấy chi tiết bài tập thất bại"
            }
        }
    }

    fun updateExercise(request: ExerciseResponse) {
        viewModelScope.launch {
            val result = exerciseRepository.updateExercise(request)
            if (result != null && result.result != null) {
                _message.value = "Cập nhật bài tập thành công"
            } else {
                _message.value = "Cập nhật bài tập thất bại"
            }
        }
    }

    fun deleteExercise(id: String) {
        viewModelScope.launch {
            val result = exerciseRepository.deleteExercise(id)
            if (result != null && result.result != null) {
                _message.value = "Xóa bài tập thành công"
            } else {
                _message.value = "Xóa bài tập thất bại"
            }
        }
    }
}
