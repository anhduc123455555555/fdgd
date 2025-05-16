package com.example.jetpackcompose2.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose2.Repository.UserRepository
import com.example.jetpackcompose2.dto.response.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class UserController(
    private val userRepository: UserRepository
) : ViewModel() {

    // State lưu danh sách users
    private val _usersState = MutableStateFlow<List<UserResponse>>(emptyList())
    val usersState: StateFlow<List<UserResponse>> = _usersState

    // State lưu user info hiện tại
    private val _userInfoState = mutableStateOf<UserResponse?>(null)
    val userInfoState: State<UserResponse?> = _userInfoState

    // State lưu kết quả update user (thông báo thành công/ thất bại)
    private val _updateState = mutableStateOf<String?>(null)
    val updateState: State<String?> = _updateState

    // State lưu user tìm theo số điện thoại
    private val _searchUserState = mutableStateOf<UserResponse?>(null)
    val searchUserState: State<UserResponse?> = _searchUserState

    // State lưu user lấy theo Id
    private val _userByIdState = mutableStateOf<UserResponse?>(null)
    val userByIdState: State<UserResponse?> = _userByIdState

    // Load danh sách users
    fun loadUsers() {
        viewModelScope.launch {
            val users = userRepository.fetchUsers()
            if (users != null) {
                _usersState.value = users
            } else {
                _usersState.value = emptyList()
            }
        }
    }

    // Load thông tin user hiện tại
    fun loadUserInfo() {
        viewModelScope.launch {
            val user = userRepository.fetchUserInfo()
            _userInfoState.value = user
        }
    }

    // Update user
    fun updateUser(userId: String, userRequest: UserResponse) {
        viewModelScope.launch {
            _updateState.value = "Đang cập nhật..."
            val updatedUser = userRepository.updateUser(userId, userRequest)
            _updateState.value = if (updatedUser != null) {
                "Cập nhật thành công"
            } else {
                "Cập nhật thất bại"
            }
        }
    }

    // Tìm user theo số điện thoại
    fun findUserByPhone(phone: String) {
        viewModelScope.launch {
            val user = userRepository.findUserByPhone(phone)
            _searchUserState.value = user
        }
    }

    // Lấy user theo Id
    fun loadUserById(userId: String) {
        viewModelScope.launch {
            val user = userRepository.fetchUserById(userId)
            _userByIdState.value = user
        }
    }

    // Có thể thêm hàm clear trạng thái nếu cần
    fun clearUpdateState() {
        _updateState.value = null
    }

    fun clearSearchUserState() {
        _searchUserState.value = null
    }
}
