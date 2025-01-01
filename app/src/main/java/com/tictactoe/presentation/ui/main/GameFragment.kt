package com.tictactoe.presentation.ui.main

import com.tictactoe.domain.viewModel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : BaseListFragment() {

    override fun getViewModelMethod(): (GameViewModel) -> Unit {
        return { viewModel.refreshGameList() }
    }
}
