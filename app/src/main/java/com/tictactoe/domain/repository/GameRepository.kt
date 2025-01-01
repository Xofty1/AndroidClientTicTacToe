package com.tictactoe.domain.repository

import android.util.Log
import androidx.compose.ui.unit.TextUnit
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.datasource.retrofit.model.GameDto
import com.tictactoe.datasource.room.DatabaseService
import datasource.mapper.GameMapperRetrofit
import datasource.mapper.GameMapperRoom
import domain.model.Game
import domain.utils.TURN
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {


    suspend fun updateGame(game: Game) {
        databaseService.updateGame(GameMapperRoom.fromDomain(game))
    }

    suspend fun joinToGame(game: Game): GameDto? {
        val updatedGame = networkService.joinToGame(game.id.toString()).getOrNull()
        if (updatedGame != null) {
            Log.d("Update345", "GameDto not null")
            databaseService.updateGame(GameMapperRoom.fromDomain(game))
        }
        Log.d("Update345", "Join")
        return updatedGame
    }

    suspend fun makeMove(game: Game, cell: Int): Game? {
        val currentUser = databaseService.getCurrentUser()
        if ((currentUser.login == game.firstUserLogin && game.turn == TURN.X) ||
            (currentUser.login == game.secondUserLogin && game.turn == TURN.O)
        ) {
            val result = networkService.makeMove(game.id.toString(), cell)
            val updatedGame = result.getOrNull()
            if (updatedGame != null) {
                val domainGame = GameMapperRetrofit.toDomain(updatedGame, game.id)
                updateGame(domainGame)
                return domainGame
            }
        }
        return null
    }

    suspend fun makeMove(game: Game): Game? {
        val result = networkService.makeMove(game.id.toString())

        val updatedGame = result.getOrNull()
        if (updatedGame != null) {
            val domainGame = GameMapperRetrofit.toDomain(updatedGame, game.id)
            updateGame(domainGame)
            return domainGame
        }
        return null
    }

    suspend fun getFirstPlayer(): String {
        return databaseService.getCurrentUser().login
    }

    suspend fun getGames(): Result<List<Game>> {
        return try {
            val response = networkService.getGames()
            val games = response.getOrNull()
            if (games != null) {
                Log.d("GAMES", games.toString())
                Result.success(games.filter { it.firstUserLogin == databaseService.getCurrentUser().login })
            } else {
                Result.failure(Exception("Error fetching games"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGame(id: String): Result<Game> {
        return try {
            val response = networkService.getGame(id)
            val game = response.getOrNull()
            if (game != null) {
                Log.d("GAMES", game.toString())
                Result.success(game)
            } else {
                Result.failure(Exception("Error fetching games"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGamesToJoin(): Result<List<Game>> {
        return try {
            val response = networkService.getGames()
            val games = response.getOrNull()
            if (games != null) {
                val currentUser = databaseService.getCurrentUser()
                val joinGames =
                    games.filter { it.secondUserLogin == null && it.firstUserLogin != currentUser.login }
                Result.success(joinGames)
            } else {
                Result.failure(Exception("Error fetching games"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}