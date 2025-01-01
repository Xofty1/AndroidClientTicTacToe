package com.tictactoe.domain.repository

import android.util.Log
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.datasource.retrofit.mapper.UserMapperRetrofit
import com.tictactoe.datasource.retrofit.model.LoginRequest
import com.tictactoe.datasource.room.DatabaseService
import com.tictactoe.datasource.room.entity.UserEntity
import com.tictactoe.domain.model.User
import datasource.mapper.CurrentUserMapperRoom
import datasource.mapper.GameMapperRetrofit
import datasource.mapper.GameMapperRoom
import datasource.mapper.UserMapperRoom
import domain.utils.AUTH_MESSSAGE
import java.util.UUID
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {


    lateinit var currentUser: User
    suspend fun registerUser(
        login: String,
        password: String,
        confirmPassword: String
    ): AUTH_MESSSAGE {
        if (login.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return AUTH_MESSSAGE.EMPTY_FIELDS
        }

//        if (!isPasswordStrong(password)) {
//            return AUTH_MESSSAGE.WEAK_PASSWORD
//        }

        val existingUser = databaseService.getUserByLogin(login)
        val isUserExist = networkService.isUserExist(LoginRequest(login))
        Log.d("TTT", isUserExist.toString())


        return if (!isUserExist) {
            if (password == confirmPassword) {
                networkService.createUser(login, password)
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
        networkService.setLoginAndPassword(login, password)

        val result = networkService.loginUser()
        val user = result.getOrNull()

        if (user != null) {
            // Преобразуем данные пользователя из ответа
            val domainUser = UserMapperRetrofit.toDomain(user)

            databaseService.clearCurrentUser()

            databaseService.insertCurrentUser(CurrentUserMapperRoom.fromDomain(domainUser))

            currentUser = domainUser

            return AUTH_MESSSAGE.SUCCESS_LOGIN
        }

        return AUTH_MESSSAGE.UNSUCCESS_LOGIN
    }


    // может быть такое что текущий пользователь не проиницмализирован
    fun getCredentials(): Pair<String, String>? {
        return if (::currentUser.isInitialized) {
            Pair(currentUser.login, currentUser.password)
        } else {
            null
        }
    }

    suspend fun isExist(login: String): Boolean {
        return networkService.isUserExist(LoginRequest(login))
    }

    private fun isPasswordStrong(password: String): Boolean {
        // Проверка: длина не менее 8 символов, наличие букв, цифр, специальных символов
        val passwordRegex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$")
        return passwordRegex.matches(password)
    }

    fun setAutoLoginAndPassword(login: String, password: String) {
//        val curLogin = databaseService.getCurrentUser()?.login
//        if (curLogin != null) {
//            try {
//                val curPassword = networkService.getPasswordByLogin(LoginRequest(curLogin))
//                if (curPassword != null)
//                    currentUser = User(
//                        login = curLogin,
//                        password = curPassword
//                    )
//            }
//            catch (_: Exception){
//
//            }
//
//        }
        networkService.setLoginAndPassword(login, password)
    }

    suspend fun getCurrentUser(): String? {
        return databaseService.getCurrentUser()?.login
    }

}