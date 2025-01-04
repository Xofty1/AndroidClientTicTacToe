package com.tictactoe.domain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.GameRepository
import datasource.mapper.GameMapperRetrofit
import domain.model.Game
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

    private val _gameState = MutableLiveData<Game>()
    val gameState: LiveData<Game> get() = _gameState

    private val _currentPlayer = MutableLiveData<String>()
    val currentPlayer: LiveData<String> get() = _currentPlayer


    val games = MutableLiveData<List<Game>>()
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun setGame(game: Game) {
        _gameState.value = game
    }

    fun getGame(): Game? {
        return _gameState.value
    }


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

    fun clearData() {
        viewModelScope.launch {
            gameRepository.clearData()
        }
    }

    fun removeGame(id: String) {
        viewModelScope.launch {
            gameRepository.deleteGame(id)
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

    fun makeMoveWithPlayer(game: Game, row: Int, col: Int) {
        viewModelScope.launch {
            gameRepository.makeMove(game, row * 3 + col)?.let {
                _gameState.postValue(it)
            }
        }
    }

    fun getCurrentPlayer() {
        viewModelScope.launch {
            gameRepository.getFirstPlayer()?.let {
                _currentPlayer.postValue(it)
            }
        }
    }


    fun joinToGame(game: Game) {
        viewModelScope.launch {
            val updatedGame = gameRepository.joinToGame(game)
            if (updatedGame != null) {
                _gameState.postValue(GameMapperRetrofit.toDomain(updatedGame, game.id))

            }
        }
    }

    fun refreshGameList() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result = gameRepository.getGames()
                result.fold(
                    onSuccess = { newGames ->
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

    private var pollingJob: Job? = null

    fun startPollingGame(game: Game) {
        pollingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    val updatedGame = gameRepository.getGame(game.id.toString()).getOrNull()
                    Log.d("Update345", "is null = $updatedGame")
                    updatedGame?.let {
                        Log.d("Update345", it.toString())
                        _gameState.postValue(it)
                    }
                } catch (e: Exception) {
                    error.postValue("Error fetching game updates: ${e.message}")
                }
                delay(1000)
            }
        }
    }

    fun stopPollingGame() {
        pollingJob?.cancel()
    }
}
