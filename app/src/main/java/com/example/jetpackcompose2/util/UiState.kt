package com.example.jetpackcompose2.util

sealed class UiState {
    object Loading : UiState()
    data class Success(val message: String) : UiState()
    data class Error(val message: String) : UiState()
}
