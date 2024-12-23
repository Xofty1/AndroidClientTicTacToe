package com.tictactoe.presentation.ui.authorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tictactoe.domain.repository.UserRepository
import com.tictactoe.domain.viewModel.AuthViewModel
import com.tictactoe.ui.theme.TIcTacToeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TIcTacToeTheme {
                AuthorizationScreen()
            }
        }
    }

    @Composable
    fun AuthorizationScreen() {
        val viewModel = AuthViewModel(userRepository)
        var isLoginScreen by remember { mutableStateOf(true) }


        Surface(modifier = Modifier.fillMaxSize()) {

            if (isLoginScreen) {
                LoginScreen(viewModel, onNavigateToRegister = { isLoginScreen = false })
            } else {
                RegistrationScreen(viewModel, onNavigateToLogin = { isLoginScreen = true })
            }
        }
    }

}




