package com.tictactoe.datasource.retrofit


import android.util.Base64
import android.util.Log
import com.tictactoe.datasource.retrofit.model.UserDto
import datasource.mapper.GameMapperRetrofit
import domain.model.Game
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID
import java.util.concurrent.TimeUnit

class NetworkService {

    private val BASE_URL = "http://192.168.43.135:8080"
    private var login: String = ""
    private var password: String = ""

    private val client: OkHttpClient by lazy {
        val itersector = HttpLoggingInterceptor()
        itersector.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val interceptor = AuthInterceptor(login, password)
                interceptor.intercept(chain)
            }.addInterceptor(itersector)
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

    private val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
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

    suspend fun loginUser(login: String, password: String): Result<UserDto> {
        return try {
            val response = userApi.loginUser()
            if (response.isSuccessful) {
                val userDto = response.body() ?: throw Exception("Empty response body")
                Result.success(userDto)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("LoginError", "Exception: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun isUserExist(login: String): Boolean {
        return try {
            // Отправляем запрос на сервер
            Log.d("isExist", "Start response")
            val response = userApi.isExist(login).awaitResponse()
            val body = response.body()?.string()
            Log.d("isExist", response.toString())
            if (response.isSuccessful) {
                body == "true"
            } else {
                throw Exception("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error checking user existence: ${e.message}")
            false // При ошибке возвращаем false
        }
    }


    private fun createBasicAuthHeader(login: String, password: String): String {
        val credentials = "$login:$password"
        val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        return "Basic $base64Credentials"
    }


    suspend fun createUser(login: String, password: String): Result<UserDto> {
        val userDto = UserDto(login, password, emptyMap())
        return try {
            Log.d("CreateUser", "Sending user creation request with login: $login")
            val response = userApi.createUser(userDto)
                .awaitResponse() // Отправляем запрос на создание пользователя
            if (response.isSuccessful) {
                val body = response.body()
                    ?: return Result.failure(Exception("Empty body")) // Проверка на пустое тело ответа
                Log.d("CreateUser", "User created successfully: ${response.message()}")
                Result.success(body) // Возвращаем успешный результат
            } else {
                Log.e("CreateUser", "Error: ${response}")
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}")) // Обработка ошибок
            }
        } catch (e: Exception) {
            Log.e("CreateUser", "Exception occurred: ${e.localizedMessage}")
            Result.failure(e) // Обработка исключений
        }
    }

    fun setLoginAndPassword(login: String, password: String) {
        this.login = login
        this.password = password
    }

    // Получаем логин и пароль
    fun getLogin(): String = login
    fun getPassword(): String = password
}



