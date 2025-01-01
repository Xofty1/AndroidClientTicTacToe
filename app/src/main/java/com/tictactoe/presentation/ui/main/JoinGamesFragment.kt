package com.tictactoe.presentation.ui.main

import com.tictactoe.domain.viewModel.GameViewModel
import com.tictactoe.presentation.ui.main.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game


@AndroidEntryPoint
class JoinGamesFragment : BaseListFragment(), GameAdapter.OnFragmentGameClickListener {

    override fun getViewModelMethod(): (GameViewModel) -> Unit {
        return { viewModel.refreshGameListJoinGames() }
    }

    override fun onFragmentGameClicked(game: Game) {
        viewModel.joinToGame(game)
    }
}
