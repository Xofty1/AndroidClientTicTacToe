package com.tictactoe.datasource.retrofit

import com.tictactoe.datasource.retrofit.model.GameDto
import domain.model.Game
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GameApi {

    @POST("/game/new")
    fun createGame(@Body gameDto: GameDto): Call<Map<String, GameDto>>

    @GET("/game/{id}")
    fun getGame(@Path("id") gameId: String): Call<GameDto>

    @GET("/games")
    fun getGames(): Call<Map<String, GameDto>>

    @POST("/game/join/{id}")
    fun joinToGame(@Path("id") gameId: String): Call<GameDto>

    @POST("/game/makeMove/{id}/{cell}")
    fun makeMove(
        @Path("id") gameId: String,
        @Path("cell") cellIndex: Int
    ): Call<GameDto>

    @POST("/game/makeMove/{id}")
    fun makeComputerMove(@Path("id") gameId: String): Call<GameDto>

    @POST("/game/makeMove/{id}")
    fun makeMoveWithComputer(
        @Path("id") gameId: String
    ): Call<GameDto>
}
