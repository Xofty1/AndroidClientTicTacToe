package com.tictactoe.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tictactoe.R
import com.tictactoe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//
//class GameFragment() : Fragment() {
//
//    @Inject
//    lateinit var gameDao: GameDao
//
//    @Inject
//    lateinit var networkService: NetworkService
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                TIcTacToeTheme {
//                    MainScreen(onCreateNewGame = {
//                        createNewGame()
//                    },
//                        onGetGames = { getGames() })
//                }
//            }
//        }
//    }
//
//    private fun createNewGame() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            try {
//                val result =
//                    networkService.createNewGame()
//                result.onSuccess { id ->
//                    println("New game created: $id")
//                    gameDao.insertGame(NewGameFactory.createNewGameEntity(id))
//                    val games = gameDao.getAllGames()
//                    games.map {
//                        println(it.id)
//                        println(it.board)
//                    }
//                }.onFailure { error ->
//                    println("Error creating game: ${error.message}")
//
//                }
//            } catch (e: Exception) {
//                println("Exception occurred: ${e.message}")
//            }
//        }
//    }
//
////    private fun getGameById(id: String) {
////        viewLifecycleOwner.lifecycleScope.launch {
////            try {
////                val result =
////                    NetworkService.getGame()
////                result.onSuccess { game ->
////                    println("New game created: $game")
////                }.onFailure { error ->
////                    println("Error creating game: ${error.message}")
////                }
////            } catch (e: Exception) {
////                println("Exception occurred: ${e.message}")
////            }
////        }
////    }
//
//    private fun getGames() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            try {
//                val result =
//                    networkService.getGames()
//                val allGames: List<Game>
//                result.onSuccess { games ->
//                    println(games.toString())
//                    allGames = games
//                    for (game in allGames) {
//                        Log.d("GameTTT", game.id.toString())
//                    }
//                }.onFailure { error ->
//                    println("Error creating game: ${error.message}")
//                }
//            } catch (e: Exception) {
//                println("Exception occurred: ${e.message}")
//            }
//        }
//    }
//}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GameFragment())
                .commit()
        }
//        setContent {
//            TIcTacToeTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Box(
//                        modifier = Modifier
//                            .padding(innerPadding)
//                            .fillMaxSize()
//                    ) {
//                        AndroidView(
//                            factory = { context ->
//                                FragmentContainerView(context).apply {
//                                    id = View.generateViewId()
//                                    layoutParams = ViewGroup.LayoutParams(
//                                        ViewGroup.LayoutParams.MATCH_PARENT,
//                                        ViewGroup.LayoutParams.MATCH_PARENT
//                                    )
//                                }
//                            },
//                            modifier = Modifier.fillMaxSize()
//                        ) { fragmentContainerView ->
//                            // Убедитесь, что Fragment добавлен только один раз
//                            if (supportFragmentManager.findFragmentById(fragmentContainerView.id) == null) {
//                                supportFragmentManager.commit {
//                                    replace(fragmentContainerView.id, GameFragment())
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
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

