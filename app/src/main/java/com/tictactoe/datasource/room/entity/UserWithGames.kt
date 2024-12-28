package com.tictactoe.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithGames(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "login", // Связь по колонке login в UserEntity
        entityColumn = "userId" // Связь по колонке userId в GameEntity
    )
    val games: List<GameEntity> // Список игр, связанных с пользователем
)