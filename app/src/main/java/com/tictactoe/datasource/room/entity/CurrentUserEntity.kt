package com.tictactoe.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user_table")
data class CurrentUserEntity(
    @PrimaryKey val id: String,
    val login: String,
    val password: String
)
