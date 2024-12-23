package com.tictactoe.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    var authResult: (Boolean) -> Unit = {}

    fun register(login: String, password: String) {
        viewModelScope.launch {
            val success = userRepository.registerUser(login, password)
            authResult(success)
        }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            val success = userRepository.loginUser(login, password)
            authResult(success)
        }
    }
}