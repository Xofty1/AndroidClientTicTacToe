package com.tictactoe.datasource.retrofit.model

data class GameDto(
    val id: String,
    val board: List<List<Int>>,
    val turn: String,
    val status: String
)
