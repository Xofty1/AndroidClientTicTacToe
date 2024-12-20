package com.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.datasource.room.TicTacToeDatabase
import com.tictactoe.datasource.room.entity.GameEntity
import com.tictactoe.ui.theme.TIcTacToeTheme
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TIcTacToeTheme {
                    MainScreen(onCreateNewGame = {
                        createNewGame()
                    },
                        onGetGames = { getGames() })
                }
            }
        }
    }

    private fun createNewGame() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val result =
                    NetworkService.createNewGame()
                result.onSuccess { game ->
                    println("New game created: $game")
                }.onFailure { error ->
                    println("Error creating game: ${error.message}")
//                    database.gameDao().insertGame(GameEntity(
//                        id = "1",
//                        board = "XOO OOXXO",
//                        status = "DRAW",
//                        turn = "X"
//                    ))
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
            }
        }
    }

//    private fun getGameById(id: String) {
//        viewLifecycleOwner.lifecycleScope.launch {
//            try {
//                val result =
//                    NetworkService.getGame()
//                result.onSuccess { game ->
//                    println("New game created: $game")
//                }.onFailure { error ->
//                    println("Error creating game: ${error.message}")
//                }
//            } catch (e: Exception) {
//                println("Exception occurred: ${e.message}")
//            }
//        }
//    }

    private fun getGames() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val result =
                    NetworkService.getGames()
                val allGames: List<Game>
                result.onSuccess { games ->
                    println(games.toString())
                    allGames = games
                    for (game in allGames) {
                        Log.d("GameTTT", game.id.toString())
                    }
                }.onFailure { error ->
                    println("Error creating game: ${error.message}")
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var database: TicTacToeDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            TIcTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        AndroidView(
                            factory = { context ->
                                FragmentContainerView(context).apply {
                                    id = View.generateViewId()
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        ) { fragmentContainerView ->
                            // Убедитесь, что Fragment добавлен только один раз
                            if (supportFragmentManager.findFragmentById(fragmentContainerView.id) == null) {
                                supportFragmentManager.commit {
                                    replace(fragmentContainerView.id, GameFragment())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(onCreateNewGame: () -> Unit, onGetGames: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onCreateNewGame
        ) {
            Text("Create New Game", color = Color.White)
        }
        Button(
            onClick = onGetGames
        ) {
            Text("Get all Games", color = Color.White)
        }

    }
}

