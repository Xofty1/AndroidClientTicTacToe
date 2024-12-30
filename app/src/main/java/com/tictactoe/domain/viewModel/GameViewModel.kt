package com.tictactoe.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.domain.repository.GameRepository
import domain.model.Game
import kotlinx.coroutines.launch

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

    private val _gameState = MutableLiveData<Game>()
    val gameState: LiveData<Game> get() = _gameState


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

    fun getCurrentPlayer(game: Game): String {
        var firstPlayer = ""
        viewModelScope.launch {
            gameRepository.getFirstPlayer().apply {
                firstPlayer = this
            }
        }
        return firstPlayer
    }
}