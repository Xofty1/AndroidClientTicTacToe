package com.tictactoe.presentation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.tictactoe.R
import com.tictactoe.databinding.FragmentTicTacToeBinding
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.domain.repository.GameRepository
import com.tictactoe.domain.viewModel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import datasource.mapper.GameMapperRetrofit
import domain.model.Game
import domain.utils.OPPONENT
import domain.utils.STATUS
import domain.utils.TURN
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TicTacToeFragment : Fragment() {
    @Inject
    lateinit var gameRepository: GameRepository
    private lateinit var gridLayout: GridLayout
    private lateinit var tvFirstPlayer: TextView
    private lateinit var tvFirstPlayerTurn: TextView
    private lateinit var tvSecondPlayer: TextView
    private lateinit var tvSecondPlayerTurn: TextView
    private lateinit var tvGameStatus: TextView
    private lateinit var binding: FragmentTicTacToeBinding
    private var currentPlayer = "X"
    private var curUserLogin = ""
    lateinit var viewModel: GameViewModel
    lateinit var game: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicTacToeBinding.inflate(layoutInflater)
        val game = arguments?.getParcelable<Game>("game")
        game?.let { this.game = it }
        setupGame()

        return binding.root
    }
    private fun setupGame() {
        viewModel = GameViewModel(gameRepository)
        viewModel.getCurrentPlayer()

        gridLayout = binding.gridLayout
        tvFirstPlayer = binding.tvFirstPlayer
        tvSecondPlayer = binding.tvSecondPlayer
        tvFirstPlayerTurn = binding.tvFirstPlayerTurn
        tvSecondPlayerTurn = binding.tvSecondPlayerTurn
        tvGameStatus = binding.tvGameStatus

        viewModel.gameState.observe(viewLifecycleOwner) { updatedGame ->
            game = updatedGame
            updateUI(updatedGame)
        }
        viewModel.currentPlayer.observe(viewLifecycleOwner) { currentPlayerLogin ->
            curUserLogin = currentPlayerLogin
            if (game.firstUserLogin == curUserLogin) {
                tvFirstPlayer.text = game.firstUserLogin
                tvSecondPlayer.text = game.secondUserLogin
                tvFirstPlayerTurn.text = "X"
                tvSecondPlayerTurn.text = "O"
            } else {
                tvFirstPlayer.text = game.secondUserLogin
                tvSecondPlayer.text = game.firstUserLogin
                tvFirstPlayerTurn.text = "O"
                tvSecondPlayerTurn.text = "X"
            }
        }

        for (row in 0 until 3) {
            for (col in 0 until 3) {

                val cell = gridLayout.getChildAt(row * 3 + col) as TextView

                when (game.board.getValueOnCell(row, col)) {
                    (TURN.X.type) -> cell.text = TURN.X.name
                    (TURN.O.type) -> cell.text = TURN.O.name
                }
                cell.setOnClickListener { onCellClick(row, col, cell) }
            }
        }
    }

    private fun onCellClick(row: Int, col: Int, cell: TextView) {
        if (cell.text.isNotEmpty()) return // Ячейка уже занята
        if (game.secondUserLogin == OPPONENT.COMPUTER.type) viewModel.makeMoveWithComputer(
            game,
            row,
            col
        )
        else viewModel.makeMoveWithPlayer(game, row, col)
    }

    private fun updateUI(updatedGame: Game) {
        if (updatedGame.firstUserLogin == curUserLogin) {
            tvFirstPlayer.text = curUserLogin
            tvSecondPlayer.text = updatedGame.secondUserLogin
            tvFirstPlayerTurn.text = "X"
            tvSecondPlayerTurn.text = if (updatedGame.secondUserLogin == null)
                "Waiting..."
            else "O"
        } else {
            tvFirstPlayer.text = curUserLogin
            tvSecondPlayer.text = updatedGame.firstUserLogin
            tvFirstPlayerTurn.text = "O"
            tvSecondPlayerTurn.text = "X"
        }
        if (game.secondUserLogin == null)
            tvGameStatus.text = "Ждем соперника"
        else
            tvGameStatus.text = when (game.status) {
                STATUS.DRAW -> "Ничья"
                STATUS.NONE -> if (game.turn == TURN.O) "Ход ноликов" else "Ход крестиков"
                STATUS.X_WIN -> if (updatedGame.firstUserLogin == curUserLogin) "Победа" else "Поражение"
                STATUS.O_WIN -> if (updatedGame.secondUserLogin == curUserLogin) "Победа" else "Поражение"
            }

        for (row in 0 until 3) {
            for (col in 0 until 3) {
                val cell = gridLayout.getChildAt(row * 3 + col) as TextView
                when (updatedGame.board.getValueOnCell(row, col)) {
                    TURN.X.type -> cell.text = TURN.X.name
                    TURN.O.type -> cell.text = TURN.O.name
                    else -> cell.text = ""
                }
            }
        }
        currentPlayer = updatedGame.turn.name
    }

    override fun onStart() {
        super.onStart()
        viewModel.startPollingGame(game)
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopPollingGame()
    }


}
