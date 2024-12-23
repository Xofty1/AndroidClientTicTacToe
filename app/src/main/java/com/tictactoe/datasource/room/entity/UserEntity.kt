package com.tictactoe.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val login: String,
    val password: String
)
