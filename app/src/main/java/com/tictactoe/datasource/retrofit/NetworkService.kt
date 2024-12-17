package com.tictactoe.datasource.retrofit


import com.tictactoe.datasource.retrofit.model.GameDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkService {

    private const val BASE_URL = "http://192.168.43.228:8080"


    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val gameApi: GameApi by lazy {
        retrofit.create(GameApi::class.java)
    }

    suspend fun createNewGame(): Result<GameDto> {
        return try {
            val response = gameApi.createGame().awaitResponse()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create game: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGames(): Result<List<GameDto>> {
        return try {
            val response = gameApi.getGames().awaitResponse()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get games: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}



