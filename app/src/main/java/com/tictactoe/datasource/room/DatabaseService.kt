package com.tictactoe.datasource.room

import com.tictactoe.datasource.room.entity.CurrentUserEntity
import com.tictactoe.datasource.room.entity.GameEntity
import com.tictactoe.datasource.room.entity.UserEntity
import java.util.UUID
import javax.inject.Inject

class DatabaseService @Inject constructor(private val db: TicTacToeDatabase) {

    private val gameDao = db.gameDao()
    private val userDao = db.userDao()
    private val currentUserDao = db.currentUserDao()

    suspend fun insertGame(game: GameEntity) = gameDao.insertGame(game)
    suspend fun getGameById(id: String): GameEntity? = gameDao.getGameById(id)
    suspend fun getAllGames(): List<GameEntity> = gameDao.getAllGames()
    suspend fun deleteGame(game: GameEntity) = gameDao.deleteGame(game)

    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun getUserById(id: String): UserEntity? = userDao.getUserById(id)
    suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()
    suspend fun deleteUser(user: UserEntity) = userDao.deleteUser(user)

    suspend fun insertCurrentUser(user: CurrentUserEntity) = currentUserDao.insertCurrentUser(user)
    suspend fun getCurrentUser(): CurrentUserEntity? = currentUserDao.getCurrentUser()
    suspend fun clearCurrentUser() = currentUserDao.clearCurrentUser()
}
