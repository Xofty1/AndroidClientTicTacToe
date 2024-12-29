package com.tictactoe.datasource.retrofit.mapper

import com.tictactoe.datasource.retrofit.model.UserDto
import com.tictactoe.domain.model.User

object UserMapperRetrofit {
    fun fromDomain(user: User): UserDto {
        return UserDto(
            login = user.login,
            password = user.password,
       )
    }

    fun toDomain(userDTO: UserDto): User {
        return User(
            login = userDTO.login,
            password = userDTO.password
        )
    }
}