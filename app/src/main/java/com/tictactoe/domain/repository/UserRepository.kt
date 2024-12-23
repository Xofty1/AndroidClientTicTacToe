package com.tictactoe.domain.repository

import com.tictactoe.datasource.room.dao.CurrentUserDao
import com.tictactoe.datasource.room.dao.UserDao
import com.tictactoe.datasource.room.entity.UserEntity
import com.tictactoe.domain.model.User
import datasource.mapper.UserMapper

class UserRepository(private val userDao: UserDao) {
    lateinit var curUserDao: User
    suspend fun registerUser(login: String, password: String): Boolean {
        val existingUser = userDao.getUserByLogin(login)
        return if (existingUser == null) {
            userDao.insertUser(UserEntity(login, password))
            true // Успешная регистрация
        } else {
            false // Пользователь с таким логином уже существует
        }
    }

    suspend fun loginUser(login: String, password: String): Boolean {
        val user = userDao.getUserByLogin(login)
        if (user?.password == password) {
            curUserDao = UserMapper.toDomain(user)
            return true
        }
        return false
    }

    // может быть такое что текущий пользователь не проиницмализирован
    fun getCredentials(): Pair<String, String>? {
        return if (::curUserDao.isInitialized) {
            Pair(curUserDao.login, curUserDao.password)
        } else {
            null
        }
    }
}
