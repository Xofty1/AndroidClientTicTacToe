package com.tictactoe.domain.utils

import android.content.Context

class PreferencesManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Сохранение логина и пароля
    fun saveUserCredentials(login: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("current_user_login", login) // Сохраняем логин
        editor.putString("current_user_password", password) // Сохраняем пароль
        editor.apply()
    }

    // Получение логина
    fun getUserLogin(): String? {
        return sharedPreferences.getString("current_user_login", null)
    }

    // Получение пароля
    fun getUserPassword(): String? {
        return sharedPreferences.getString("current_user_password", null)
    }

    // Очистка данных пользователя
    fun clearUserCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("current_user_login")
        editor.remove("current_user_password")
        editor.apply()
    }
}
