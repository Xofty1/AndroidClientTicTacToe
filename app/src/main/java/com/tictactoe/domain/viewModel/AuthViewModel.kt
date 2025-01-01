package com.tictactoe.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.AuthRepository
import com.tictactoe.domain.utils.PreferencesManager
import domain.utils.AUTH_MESSSAGE
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository, private val preferencesManager: PreferencesManager) : ViewModel() {
    var authResult: (AUTH_MESSSAGE) -> Unit = {}

    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated

    fun register(login: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            val success = authRepository.registerUser(login, password, confirmPassword)
            authResult(success)
        }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.loginUser(login, password)
            if (success == AUTH_MESSSAGE.SUCCESS_LOGIN) {
                // Сохраняем логин и пароль в SharedPreferences
                preferencesManager.saveUserCredentials(login, password)
            }
            authResult(success)
        }
    }

    fun checkAuth(): Boolean {
//        viewModelScope.launch {
//            val curLogin = authRepository.getCurrentUser()
//            _isAuthenticated.value = (curLogin != null) && (authRepository.isExist(curLogin))
//        }
        return preferencesManager.getUserLogin() != null
    }

    fun loginAuto() {
//        viewModelScope.launch {
//            authRepository.setAutoLoginAndPassword()
//        }
        authRepository.setAutoLoginAndPassword(preferencesManager.getUserLogin() ?: "",preferencesManager.getUserPassword() ?: "")
    }
}