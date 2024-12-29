package com.tictactoe.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tictactoe.datasource.room.entity.CurrentUserEntity

@Dao
interface CurrentUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(user: CurrentUserEntity)

    @Query("SELECT * FROM current_user_table LIMIT 1")
    suspend fun getCurrentUser(): CurrentUserEntity

    @Query("DELETE FROM current_user_table")
    suspend fun clearCurrentUser()
}
