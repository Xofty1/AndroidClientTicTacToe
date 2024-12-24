package com.tictactoe.domain.repository

import com.tictactoe.datasource.room.DatabaseService
import com.tictactoe.datasource.room.entity.UserEntity
import com.tictactoe.domain.model.User
import datasource.mapper.UserMapper
import domain.utils.AUTH_MESSSAGE
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val databaseService: DatabaseService
) {
    lateinit var currentUser: User
    suspend fun registerUser(login: String, password: String, confirmPassword: String): AUTH_MESSSAGE {
        if (login.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return AUTH_MESSSAGE.EMPTY_FIELDS
        }

//        if (!isPasswordStrong(password)) {
//            return AUTH_MESSSAGE.WEAK_PASSWORD
//        }

        val existingUser = databaseService.getUserByLogin(login)
        return if (existingUser == null) {
            if (password == confirmPassword) {
                databaseService.insertUser(UserEntity(login, password))
                AUTH_MESSSAGE.SUCCESS_REGISTER
            } else {
                AUTH_MESSSAGE.PASSWORD_CONFLICT
            }
        } else {
            AUTH_MESSSAGE.USER_CONFLICT
        }
    }

    suspend fun loginUser(login: String, password: String): AUTH_MESSSAGE {
        val user = databaseService.getUserByLogin(login)
        return if (user?.password == password) {
            currentUser = UserMapper.toDomainFromEntity(user)
            AUTH_MESSSAGE.SUCCESS_LOGIN
        }
        else AUTH_MESSSAGE.UNSUCCESS_LOGIN
    }

    // может быть такое что текущий пользователь не проиницмализирован
    fun getCredentials(): Pair<String, String>? {
        return if (::currentUser.isInitialized) {
            Pair(currentUser.login, currentUser.password)
        } else {
            null
        }
    }

    private fun isPasswordStrong(password: String): Boolean {
        // Проверка: длина не менее 8 символов, наличие букв, цифр, специальных символов
        val passwordRegex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$")
        return passwordRegex.matches(password)
    }
}
