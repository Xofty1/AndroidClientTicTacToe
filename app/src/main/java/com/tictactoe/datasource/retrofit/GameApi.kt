package com.tictactoe.datasource.retrofit

import com.tictactoe.datasource.retrofit.model.GameDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameApi {

    @POST("/game/new")
    fun createGame(@Body gameDto: GameDto): Call<Map<String, GameDto>>

    @GET("/game/{id}")
    fun getGame(@Path("id") gameId: String): Call<GameDto>

    @GET("/game/delete/{id}")
    fun deleteGame(@Path("id") gameId: String): Call<Boolean>

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
