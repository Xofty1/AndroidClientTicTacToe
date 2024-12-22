package com.tictactoe.presentation.ui.authorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

class AuthorizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthorizationScreen()
        }
    }
}

@Composable
fun AuthorizationScreen() {
    var isLoginScreen by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoginScreen) {
            LoginScreen(onNavigateToRegister = { isLoginScreen = false })
        } else {
            RegistrationScreen(onNavigateToLogin = { isLoginScreen = true })
        }
    }
}


