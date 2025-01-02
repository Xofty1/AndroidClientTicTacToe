package com.tictactoe.datasource.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tictactoe.datasource.room.entity.GameEntity

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Query("SELECT * FROM game_table WHERE id = :id")
    suspend fun getGameById(id: String): GameEntity?

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<GameEntity>

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("DELETE FROM game_table WHERE id = :id")
    suspend fun deleteGameById(id: String)


    @Delete
    suspend fun deleteGame(game: GameEntity)

    @Update
    suspend fun updateGame(game: GameEntity)
}
