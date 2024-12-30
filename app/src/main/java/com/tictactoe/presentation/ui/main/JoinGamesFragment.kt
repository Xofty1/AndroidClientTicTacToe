package com.tictactoe.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tictactoe.R
import com.tictactoe.databinding.FragmentGameBinding
import com.tictactoe.domain.repository.GameRepository
import com.tictactoe.domain.viewModel.GameViewModel
import com.tictactoe.presentation.ui.main.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game
import javax.inject.Inject


@AndroidEntryPoint
class JoinGamesFragment : Fragment(), GameAdapter.OnFragmentGameClickListener {
    @Inject
    lateinit var gameRepository: GameRepository
    lateinit var viewModel: GameViewModel
    private lateinit var binding : FragmentGameBinding
    private lateinit var gameAdapter: GameAdapter
    private val games = mutableListOf<Game>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(layoutInflater)
        viewModel = GameViewModel(gameRepository)

        gameAdapter = GameAdapter(requireContext(), games, activity as GameAdapter.OnGameClickListener)
        binding.list.adapter = gameAdapter

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshGameListJoinGames()
        }

        viewModel.games.observe(viewLifecycleOwner) { newGames ->
            gameAdapter.updateGames(newGames)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swiperefresh.isRefreshing = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner, { errorMessage ->
        })

        viewModel.refreshGameListJoinGames()

        return binding.root
    }

    override fun onFragmentGameClicked(game: Game) {
        viewModel.joinToGame(game)
    }
}