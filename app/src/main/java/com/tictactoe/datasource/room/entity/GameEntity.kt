package com.tictactoe.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey val id: String,
    val board: String,
    val turn: String,
    val status: String
)
