package com.tictactoe.datasource.retrofit.model

data class GameDto(
    val board: Array<IntArray>,
    val turn: String,
    val status: String,
    var firstUserLogin: String,
    var secondUserLogin: String? = null,
)
