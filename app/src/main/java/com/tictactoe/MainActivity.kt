package com.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.datasource.retrofit.model.GameDto
import com.tictactoe.ui.theme.TIcTacToeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TIcTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        onCreateNewGame = {
                            createNewGame()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun createNewGame() {
        NetworkService.gameApi.createGame().enqueue(object : Callback<GameDto> {
            override fun onResponse(call: Call<GameDto>, response: Response<GameDto>) {
                if (response.isSuccessful) {
                    val game = response.body()
                    println("New game created: $game")
                } else {
                    println("Failed to create game: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GameDto>, t: Throwable) {
                println("Error creating game: ${t.message}")
            }
        })
    }

}

@Composable
fun MainScreen(onCreateNewGame: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Greeting(name = "Android")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onCreateNewGame) {
            Text("Create New Game")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TIcTacToeTheme {
        MainScreen(onCreateNewGame = {})
    }
}
