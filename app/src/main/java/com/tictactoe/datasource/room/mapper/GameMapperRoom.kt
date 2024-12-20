package datasource.mapper

import androidx.core.graphics.PathUtils.flatten
import com.tictactoe.datasource.retrofit.model.GameDto
import com.tictactoe.datasource.room.entity.GameEntity
import domain.model.Game
import domain.model.GameBoard
import domain.utils.STATUS
import domain.utils.TURN
import java.util.UUID

object GameMapperRoom {
    fun fromDomain(game: Game): GameEntity {
        return GameEntity(
            id = game.id.toString(),
            board = game.board.board.flatMap { it.toList() }
                .joinToString("") { if (it == 1) "X" else if (it == -1) "O" else " " },
            turn = game.turn.name,
            status = game.status.result
        )
    }


    fun toDomain(gameEntity: GameEntity): Game {
        return Game(
            id = UUID.fromString(gameEntity.id),
            board = GameBoard(gameEntity.board),
            turn = when (gameEntity.turn) {
                ("X") -> TURN.X
                ("O") -> TURN.O
                else -> TURN.NONE
            },
            status = when (gameDTO.status) {
                ("X WON") -> STATUS.X_WIN
                ("O WON") -> STATUS.O_WIN
                ("DRAW") -> STATUS.DRAW
                else -> STATUS.NONE
            },
        )
    }

    fun stringToBoard(input: String, size: Int): Array<IntArray> {
        // Проверяем, что длина строки подходит для создания двумерного массива заданного размера
        require(input.length == size * size) { "Input length must match the board size squared" }

        // Преобразуем строку в двумерный массив
        return Array(size) { row ->
            IntArray(size) { col ->
                when (input[row * size + col]) {
                    'X' -> 1  // Преобразуем 'X' в 1
                    'O' -> 2  // Преобразуем 'O' в 2
                    else -> 0 // Все остальные символы в 0
                }
            }
        }
    }
}
