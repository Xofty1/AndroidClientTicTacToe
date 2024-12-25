package com.tictactoe.domain.model

import domain.model.Game
import java.util.concurrent.CopyOnWriteArrayList

data class User (
    val login: String,
    val password: String,
    val games: CopyOnWriteArrayList<Game>
)