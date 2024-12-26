package com.tictactoe.datasource.retrofit

import com.tictactoe.datasource.retrofit.model.UserDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
        @POST("/user/new")
        fun createUser(@Body userDto: UserDto): Call<UserDto>

        @POST("user/exist")
        fun isExist(@Body login: String): Call<ResponseBody>

        @POST("user/login")
        suspend fun loginUser(): Response<UserDto>

    }