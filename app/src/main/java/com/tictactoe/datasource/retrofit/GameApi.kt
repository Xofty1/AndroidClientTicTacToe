package com.tictactoe.datasource.retrofit

import com.tictactoe.datasource.retrofit.model.GameDto
import retrofit2.Call
import retrofit2.http.*

interface GameApi {

    @POST("/game/new")
    fun createGame(): Call<GameDto>

    @GET("/game/{id}")
    fun getGame(@Path("id") gameId: String): Call<GameDto>

    @POST("/game/makeMove/{id}/{cell}")
    fun makeMove(
        @Path("id") gameId: String,
        @Path("cell") cellIndex: Int
    ): Call<GameDto>

    @POST("/game/makeMove/{id}")
    fun makeMoveWithComputer(
        @Path("id") gameId: String
    ): Call<GameDto>
}