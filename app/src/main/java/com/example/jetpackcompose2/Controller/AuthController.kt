    package com.example.jetpackcompose2.controller

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.jetpackcompose2.Repository.AuthRepository
    import kotlinx.coroutines.launch
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.State
    import com.example.jetpackcompose2.dto.request.CreateRequest
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow

    class AuthController(
        private val authRepository: AuthRepository
    ) : ViewModel() {

        private val _loginState = mutableStateOf<String?>(null)
        val loginState: State<String?> = _loginState

        private val _registerState = MutableStateFlow<String?>(null)
        val registerState: StateFlow<String?> = _registerState

        fun login(username: String, password: String) {
            viewModelScope.launch {
                _loginState.value = "Loading..." // Hiển thị loading khi đang gửi request

                val isLoginSuccessful = authRepository.login(username, password)

                _loginState.value = if (isLoginSuccessful) {
                    "Đăng nhập thành công" // Thông báo nếu đăng nhập thành công
                } else {
                    "Đăng nhập thất bại" // Thông báo nếu đăng nhập thất bại
                }
            }
        }
        fun clearLoginState() {
            _loginState.value = null
        }


        fun register(username: String, password: String, phone: String, dob: String) {
            viewModelScope.launch {
                _registerState.value = "Đang đăng ký..." // Hiển thị trạng thái loading khi đang gửi yêu cầu đăng ký

                val createRequest = CreateRequest(
                    username = username,
                    password = password,
                    phone = phone,
                    dob = dob
                )

                // Gửi yêu cầu đăng ký đến repository
                val isRegisterSuccessful = authRepository.register(createRequest)

                _registerState.value = if (isRegisterSuccessful) {
                    "Đăng ký thành công" // Thông báo khi đăng ký thành công
                } else {
                    "Đăng ký thất bại" // Thông báo khi đăng ký thất bại
                }
            }
        }




        suspend fun refreshToken(): String? {
            return authRepository.refreshAccessToken()
        }
    }