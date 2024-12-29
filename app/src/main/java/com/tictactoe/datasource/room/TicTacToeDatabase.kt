package com.tictactoe.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tictactoe.datasource.room.dao.CurrentUserDao
import com.tictactoe.datasource.room.dao.GameDao
import com.tictactoe.datasource.room.dao.UserDao
import com.tictactoe.datasource.room.entity.CurrentUserEntity
import com.tictactoe.datasource.room.entity.GameEntity
import com.tictactoe.datasource.room.entity.UserEntity

@Database(
    entities = [GameEntity::class, UserEntity::class, CurrentUserEntity::class],
    version = 3,
    exportSchema = false
)
abstract class TicTacToeDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun userDao(): UserDao
    abstract fun currentUserDao(): CurrentUserDao
}
