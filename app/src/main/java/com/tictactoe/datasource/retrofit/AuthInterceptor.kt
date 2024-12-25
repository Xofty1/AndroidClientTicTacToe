package com.tictactoe.datasource.retrofit

import android.util.Base64
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val login: String, private val password: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Если данные о логине и пароле есть, добавляем заголовок
        Log.d("isExist", "$login $password")

        val authenticatedRequest = if (login.isNotBlank() && password.isNotBlank()) {
            val authHeader = createBasicAuthHeader(login, password)

            request.newBuilder()
                .addHeader("Authorization", authHeader)
                .build()

        } else {
            request
        }
        Log.d("isExist", request.toString())
        return chain.proceed(authenticatedRequest)
    }

    private fun createBasicAuthHeader(login: String, password: String): String {
        val credentials = "$login:$password"
        val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        return "Basic $base64Credentials"
    }
}

