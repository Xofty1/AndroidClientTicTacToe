package com.tictactoe.datasource.retrofit.mapper

import com.tictactoe.datasource.retrofit.model.UserDto
import com.tictactoe.domain.model.User
import datasource.mapper.GameMapperRetrofit
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

object UserMapperRetrofit {
    fun fromDomain(user: User): UserDto {
        return UserDto(
            login = user.login,
            password = user.password,
            games = user.games.associate { it.id.toString() to GameMapperRetrofit.fromDomain(game = it) },
        )
    }

    fun toDomain(userDTO: UserDto): User {
        return User(
            login = userDTO.login,
            password = userDTO.password,
            games = CopyOnWriteArrayList(
                userDTO.games.map { (id, gameDTO) ->
                    GameMapperRetrofit.toDomain(gameDTO, UUID.fromString(id))
                }
            )

        )
    }
}