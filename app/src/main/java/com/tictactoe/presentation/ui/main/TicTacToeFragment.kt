package com.tictactoe.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import com.tictactoe.R
import domain.model.Game

class TicTacToeFragment(val game: Game) : Fragment() {

    private lateinit var gridLayout: GridLayout
    private var currentPlayer = "X"
    private val board = Array(3) { Array(3) { "" } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false)
        gridLayout = view.findViewById(R.id.gridLayout)
        setupGame()
        return view
    }

    private fun setupGame() {
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                val cell = gridLayout.getChildAt(row * 3 + col) as TextView
                cell.setOnClickListener { onCellClick(row, col, cell) }
            }
        }
    }

    private fun onCellClick(row: Int, col: Int, cell: TextView) {
        if (cell.text.isNotEmpty()) return // Ячейка уже занята

        cell.text = currentPlayer
        board[row][col] = currentPlayer

        if (checkWin(row, col)) {
            Toast.makeText(requireContext(), "Игрок $currentPlayer выиграл!", Toast.LENGTH_LONG).show()
            resetGame()
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        // Проверяем строку, столбец и диагонали
        return (board[row].all { it == currentPlayer } ||
                board.all { it[col] == currentPlayer } ||
                (row == col && board.indices.all { board[it][it] == currentPlayer }) ||
                (row + col == 2 && board.indices.all { board[it][2 - it] == currentPlayer }))
    }

    private fun resetGame() {
        board.forEach { it.fill("") }
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as TextView).text = ""
        }
        currentPlayer = "X"
    }
}
