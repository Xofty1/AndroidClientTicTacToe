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
            childColumns = ["userId"], // Поле userId в GameEntity
            onDelete = ForeignKey.CASCADE // Удаление игр при удалении пользователя
        )
    ],
    indices = [Index(value = ["userId"])] // Создание индекса для ускорения запросов
)
data class GameEntity(
    @PrimaryKey val id: String,
    val board: String,
    val turn: String,
    val status: String,
    val userId: String // Внешний ключ для связи с UserEntity
)
