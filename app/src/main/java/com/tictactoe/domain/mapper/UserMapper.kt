package datasource.mapper

import com.tictactoe.datasource.room.dao.UserDao
import com.tictactoe.datasource.room.entity.GameEntity
import com.tictactoe.datasource.room.entity.UserEntity
import com.tictactoe.domain.model.User
import domain.model.Game
import domain.model.GameBoard
import domain.utils.STATUS
import domain.utils.TURN
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

object UserMapper {
    fun toEntityFromDomain(user: User): UserEntity {
        return UserEntity(
            login = user.login,
            password = user.password
        )
    }

    fun toDomainFromEntity(userEntity: UserEntity): User {
        return User(
            login = userEntity.login,
            password = userEntity.password
        )
    }

//    fun toDtoFromDomain(user: User): User {
//        return UserEntity(
//            login = user.login,
//            password = user.password
//        )
//    }

//    fun toDomainFromDto(userEntity: UserDto): User {
//        return User(
//            login = userEntity.login,
//            password = userEntity.password
//        )
//    }
}
