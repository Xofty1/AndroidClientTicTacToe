package com.tictactoe.datasource.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tictactoe.datasource.room.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE login = :login")
    suspend fun getUserByLogin(login: String): UserEntity?

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserEntity>

    @Delete
    suspend fun deleteUser(user: UserEntity)

//    @Transaction
//    @Query("SELECT * FROM user_table")
//    suspend fun getUserWithGames(): List<UserWithGames>
}
