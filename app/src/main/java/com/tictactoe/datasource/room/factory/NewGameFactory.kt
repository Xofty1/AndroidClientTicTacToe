package com.tictactoe.datasource.room.factory

import com.tictactoe.datasource.room.entity.GameEntity
import domain.utils.STATUS

object NewGameFactory {
    fun createNewGameEntity(id: String): GameEntity{
        return GameEntity(
            id = id,
            board = " ".repeat(9),
            turn = "X",
            status = STATUS.NONE.result
        )
    }
}