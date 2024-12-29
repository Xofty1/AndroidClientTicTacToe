package com.tictactoe.datasource.room.factory

import com.tictactoe.datasource.room.entity.GameEntity
import domain.model.Game
import domain.model.GameBoard
import domain.utils.STATUS
import domain.utils.TURN
import java.util.ArrayList
import java.util.UUID

object NewGameFactory {
    fun createNewGameEntity(id: String, login: String): GameEntity{
        return GameEntity(
            id = id,
            board = " ".repeat(9),
            turn = "X",
            status = STATUS.NONE.result,
            firstUserLogin = login
        )
    }

    fun createNewGame(id: String, login: String, secondUserLogin: String?): Game {
        return Game(
            id = UUID.fromString(id),
            board = GameBoard(),
            turn = TURN.X,
            status = STATUS.NONE,
            firstUserLogin = login,
            secondUserLogin = secondUserLogin
        )
    }
}