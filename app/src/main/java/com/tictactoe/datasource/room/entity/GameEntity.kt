package com.tictactoe.datasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "game_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class, // Связь с UserEntity
            parentColumns = ["login"], // Поле login в UserEntity
            childColumns = ["firstUserLogin"], // Поле userId в GameEntity
            onDelete = ForeignKey.CASCADE // Удаление игр при удалении пользователя
        ),
    ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["login"],
        childColumns = ["secondUserLogin"],
        onDelete = ForeignKey.SET_NULL
    )
    ],
    indices = [Index(value = ["firstUserLogin"]), Index(value = ["secondUserLogin"])]
)
data class GameEntity(
    @PrimaryKey val id: String,
    val board: String,
    val turn: String,
    val status: String,
    var firstUserLogin: String,
    var secondUserLogin: String? = null,
)
