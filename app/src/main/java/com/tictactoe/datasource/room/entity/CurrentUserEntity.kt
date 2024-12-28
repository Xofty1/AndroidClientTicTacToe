package com.tictactoe.datasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "current_user_table",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["login"],
        childColumns = ["login"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CurrentUserEntity(
    @PrimaryKey val login: String
)
