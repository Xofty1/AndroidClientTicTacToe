package com.tictactoe.datasource.retrofit

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Получение логина и пароля из хранилища (например, SharedPreferences)
        val username = "yourUsername" // Замените на получение из базы данных
        val password = "yourPassword" // Замените на получение из базы данных

        // Формирование Basic auth
        val credentials = "$username:$password"
        val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        // Добавление заголовка Authorization
        requestBuilder.addHeader("Authorization", basicAuth)

        return chain.proceed(requestBuilder.build())
    }
}
