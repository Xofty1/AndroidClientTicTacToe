package com.tictactoe.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.AuthRepository
import domain.utils.AUTH_MESSSAGE
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var authResult: (AUTH_MESSSAGE) -> Unit = {}

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
}