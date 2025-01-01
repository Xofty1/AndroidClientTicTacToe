package com.tictactoe.datasource.retrofit.model

data class UserDto(
    val login: String,
    val password: String
)

data class LoginRequest(val login: String)