package domain.model

import com.tictactoe.domain.utils.UUIDSerializer
import domain.utils.STATUS
import domain.utils.TURN
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Game(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val board: GameBoard,
    var turn: TURN,
    var status: STATUS = STATUS.NONE
)