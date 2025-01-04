package domain.model

import android.os.Parcelable
import domain.utils.STATUS
import domain.utils.TURN
import kotlinx.parcelize.Parcelize
import java.util.UUID
import java.io.Serializable
@Parcelize
data class Game(
    val id: UUID,
    val board: GameBoard,
    var turn: TURN,
    var status: STATUS = STATUS.NONE,
    var firstUserLogin: String,
    var secondUserLogin: String? = null,
) : Parcelable