package com.tictactoe.datasource.retrofit.model

data class UserDto(
    val login: String,
    val password: String,
    val games: Map<String, GameDto>
)

data class LoginRequest(
    val login: String,
    val password: String
)