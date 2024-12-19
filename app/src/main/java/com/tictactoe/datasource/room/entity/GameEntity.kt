package com.tictactoe.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey val id: UUID,
    val board: String,
    val turn: String,
    val status: String
)
