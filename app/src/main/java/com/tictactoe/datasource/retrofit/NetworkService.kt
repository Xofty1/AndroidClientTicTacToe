package com.tictactoe.datasource.retrofit


import android.util.Log
import com.tictactoe.datasource.retrofit.model.GameDto
import com.tictactoe.domain.repository.UserRepository
import datasource.mapper.GameMapperRetrofit
import domain.model.Game
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkService(val userRepository: UserRepository) {

    private val BASE_URL = "http://192.168.43.135:8080"


    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userRepository))
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

    suspend fun createNewGame(): Result<String> {
        return try {
            val response = gameApi.createGame().awaitResponse()
            val body = response.body()?.string()
            if (!body.isNullOrEmpty()) {
                Result.success(body)
            } else {
                Result.failure(Exception("Empty response body"))
            }
        } catch (e: Exception) {
            Log.d("TTTT", "52 " + e.message)
            Result.failure(e)
        }
    }

    suspend fun getGames(): Result<List<Game>> {
        return try {
            val response = gameApi.getGames().awaitResponse()
            if (response.isSuccessful) {
                val body = response.body() ?: return Result.success(emptyList())

                val games = body.map { (idString, gameDto) ->
                    val id = UUID.fromString(idString)
                    GameMapperRetrofit.toDomain(gameDto, id)
                }

                Result.success(games)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGame(id: String): Result<Game> {
        return try {
            val response = gameApi.getGame(id).awaitResponse()
            if (response.isSuccessful) {
                val body = response.body() ?: return Result.failure(Exception("Empty body"))

                val gameId = UUID.fromString(body.first)
                val gameDto = body.second
                val game = GameMapperRetrofit.toDomain(gameDto, gameId)

                Result.success(game)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}



