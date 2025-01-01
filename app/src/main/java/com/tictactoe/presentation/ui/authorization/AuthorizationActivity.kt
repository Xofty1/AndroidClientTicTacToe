package com.tictactoe.presentation.ui.authorization

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tictactoe.domain.repository.AuthRepository
import com.tictactoe.domain.utils.PreferencesManager
import com.tictactoe.domain.viewModel.AuthViewModel
import com.tictactoe.presentation.ui.main.MainActivity
import com.tictactoe.ui.theme.TIcTacToeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationActivity : ComponentActivity() {
    @Inject
    lateinit var authRepository: AuthRepository

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AuthViewModel(authRepository, PreferencesManager(applicationContext))
        if (viewModel.checkAuth()) {
            startActivity(Intent(this, MainActivity::class.java))
            viewModel.loginAuto()
            finish()
        } else

            setContent {
                TIcTacToeTheme {
                    AuthorizationScreen(viewModel)
                }
            }
    }

}


@Composable
fun AuthorizationScreen(viewModel: AuthViewModel) {
    var isLoginScreen by remember { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoginScreen) {
            LoginScreen(viewModel, onNavigateToRegister = { isLoginScreen = false })
        } else {
            RegistrationScreen(viewModel, onNavigateToLogin = { isLoginScreen = true })
        }
    }

}

@Composable
fun LoadingScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}




