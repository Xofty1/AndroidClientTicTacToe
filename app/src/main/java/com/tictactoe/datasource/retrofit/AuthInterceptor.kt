package com.tictactoe.datasource.retrofit

import android.util.Base64
import com.tictactoe.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authRepository: AuthRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Получение логина и пароля из базы
        val credentials = runBlocking { authRepository.getCredentials() }

        // Если данные есть, добавляем заголовок
        val authenticatedRequest = if (credentials != null) {
            val (login, password) = credentials
            val authHeader = createBasicAuthHeader(login, password)

            request.newBuilder()
                .addHeader("Authorization", authHeader)
                .build()
        } else {
            request
        }

        return chain.proceed(authenticatedRequest)
    }

    private fun createBasicAuthHeader(login: String, password: String): String {
        val credentials = "$login:$password"
        val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        return "Basic $base64Credentials"
    }
}
