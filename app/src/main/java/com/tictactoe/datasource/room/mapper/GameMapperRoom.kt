package datasource.mapper

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
            board = GameBoard(stringToBoard(gameEntity.board, 3)),
            turn = when (gameEntity.turn) {
                ("X") -> TURN.X
                ("O") -> TURN.O
                else -> TURN.NONE
            },
            status = when (gameEntity.status) {
                STATUS.X_WIN.result -> STATUS.X_WIN
                STATUS.O_WIN.result -> STATUS.O_WIN
                STATUS.DRAW.result -> STATUS.DRAW
                else -> STATUS.NONE
            },
        )
    }

    fun stringToBoard(input: String, size: Int): Array<IntArray> {
        require(input.length == size * size) { "Input length must match the board size squared" }

        return Array(size) { row ->
            IntArray(size) { col ->
                when (input[row * size + col]) {
                    'X' -> 1  // Преобразуем 'X' в 1
                    'O' -> -1  // Преобразуем 'O' в 2
                    else -> 0 // Все остальные символы в 0
                }
            }
        }
    }
}
