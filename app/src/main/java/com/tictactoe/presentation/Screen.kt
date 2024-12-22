package com.tictactoe.presentation

sealed class Screen(val route: String) {
    object Authorization : Screen("Authorization")
    object Main : Screen("Main")
}