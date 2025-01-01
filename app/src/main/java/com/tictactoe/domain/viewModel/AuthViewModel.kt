package com.tictactoe.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.AuthRepository
import domain.utils.AUTH_MESSSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var authResult: (AUTH_MESSSAGE) -> Unit = {}

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()



    fun register(login: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            val success = authRepository.registerUser(login, password, confirmPassword)
            authResult(success)
        }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.loginUser(login, password)
            authResult(success)
        }
    }

    fun checkAuth() {
        viewModelScope.launch {
            val curLogin = authRepository.getCurrentUser()
            _isAuthenticated.value = (curLogin != null) && (authRepository.isExist(curLogin))
        }
    }

    fun loginAuto() {
        viewModelScope.launch {
            authRepository.setAutoLoginAndPassword()
        }
    }
}