package com.tictactoe.datasource.retrofit

import com.tictactoe.datasource.retrofit.model.UserDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("/user/new")
    fun createGame(): Call<ResponseBody>

    @GET("/user")
    fun getUser(): Call<UserDto>
}
