package datasource.mapper

import com.tictactoe.datasource.room.entity.CurrentUserEntity
import com.tictactoe.datasource.room.entity.GameEntity
import com.tictactoe.datasource.room.entity.UserEntity
import com.tictactoe.domain.model.User
import domain.model.Game
import domain.model.GameBoard
import domain.utils.STATUS
import domain.utils.TURN
import java.util.UUID

object CurrentUserMapperRoom {
    fun fromDomain(user: User): CurrentUserEntity {
        return CurrentUserEntity(
            login = user.login,
        )
    }
}
