package com.tictactoe.domain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.GameRepository
import domain.model.Game
import kotlinx.coroutines.launch

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

    private val _gameState = MutableLiveData<Game>()
    val gameState: LiveData<Game> get() = _gameState

    private val _currentPlayer = MutableLiveData<String>()
    val currentPlayer: LiveData<String> get() = _currentPlayer

    val games = MutableLiveData<List<Game>>()
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun updateGame(game: Game) {
        viewModelScope.launch {
            gameRepository.updateGame(game)
        }
    }

    fun makeMove(game: Game, row: Int, col: Int) {
        viewModelScope.launch {
            gameRepository.makeMove(game, row * 3 + col)?.let {
                _gameState.postValue(it)
            }
        }
    }

    fun makeMove(game: Game) {
        viewModelScope.launch {
            gameRepository.makeMove(game)?.let {
                _gameState.postValue(it)
            }
        }
    }

    fun makeMoveWithComputer(game: Game, row: Int, col: Int) {
        viewModelScope.launch {
            gameRepository.makeMove(game, row * 3 + col)?.let {
                _gameState.postValue(it)
            }
            gameRepository.makeMove(game)?.let {
                _gameState.postValue(it)
            }
        }
    }

    fun getCurrentPlayer(){
        viewModelScope.launch {
            gameRepository.getFirstPlayer().apply {
                _currentPlayer.postValue(this)
            }
        }
    }

    fun joinToGame(game: Game){ TODO()
    }

    fun refreshGameList() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result = gameRepository.getGames()
                result.fold(
                    onSuccess = { newGames ->
                        Log.d("GAMES", newGames.toString())

                        games.value = newGames // Используйте setValue, а не postValue
                    },
                    onFailure = { error ->
                        this@GameViewModel.error.value = "Error fetching games"
                    }
                )
            } catch (e: Exception) {
                this@GameViewModel.error.value = "Unexpected error"
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun refreshGameListJoinGames() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result = gameRepository.getGamesToJoin()
                result.fold(
                    onSuccess = { newGames ->
                        Log.d("GAMES", newGames.toString())

                        games.value = newGames // Используйте setValue, а не postValue
                    },
                    onFailure = { error ->
                        this@GameViewModel.error.value = "Error fetching games"
                    }
                )
            } catch (e: Exception) {
                this@GameViewModel.error.value = "Unexpected error"
            } finally {
                isLoading.postValue(false)
            }
        }
    }

}