package com.tictactoe.presentation.ui.authorization

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tictactoe.domain.viewModel.AuthViewModel
import com.tictactoe.presentation.ui.main.MainActivity

@Composable
fun LoginScreen(viewModel: AuthViewModel, onNavigateToRegister: () -> Unit) {
    val context = LocalContext.current
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            label = "Пароль"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.authResult = { success ->
                    if (success) {

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    } else println("Ошибка авторизации!")
                }
                viewModel.login(login, password)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Регистрация")
        }
    }
}