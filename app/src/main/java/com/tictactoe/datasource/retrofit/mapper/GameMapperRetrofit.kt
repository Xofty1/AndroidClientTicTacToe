package datasource.mapper

import com.tictactoe.datasource.retrofit.model.GameDto
import domain.model.Game
import domain.model.GameBoard
import domain.utils.STATUS
import domain.utils.TURN
import java.util.UUID

object GameMapperRetrofit {
    fun fromDomain(game: Game): GameDto {
        return GameDto(
            board = game.board.board,
            turn = game.turn.name,
            status = game.status.result
        )
    }


    fun toDomain(gameDTO: GameDto, id: UUID): Game {
        return Game(
            id = id,
            board = GameBoard(gameDTO.board),
            turn = when (gameDTO.turn) {
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
}
