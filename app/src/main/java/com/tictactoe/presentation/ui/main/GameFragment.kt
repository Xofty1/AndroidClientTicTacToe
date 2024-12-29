package com.tictactoe.presentation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tictactoe.R
import com.tictactoe.databinding.FragmentGameBinding
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.datasource.room.DatabaseService
import com.tictactoe.datasource.room.entity.GameEntity
import com.tictactoe.presentation.ui.main.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment() {
    @Inject
    lateinit var networkService: NetworkService
    private lateinit var binding : FragmentGameBinding
    private lateinit var gameAdapter: GameAdapter
    private val games = mutableListOf<Game>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(layoutInflater)
        // Set up the adapter
        gameAdapter = GameAdapter(requireContext(), games, activity as GameAdapter.OnGameClickListener)
        binding.list.adapter = gameAdapter
        refreshGameList()
        // Set up SwipeRefreshLayout
        binding.swiperefresh.setOnRefreshListener {
            refreshGameList()
        }


        return binding.root
    }

    private fun refreshGameList() {
        lifecycleScope.launch {
            binding.swiperefresh.isRefreshing = true

            try {
                val result = networkService.getGames()
                result.fold(
                    onSuccess = { newGames ->
                        games.clear()
                        games.addAll(newGames)
                        gameAdapter.updateGames(games)
                    },
                    onFailure = { error ->
                        // Обработайте ошибку
                        Log.e("GameList", "Error fetching games", error)
                    }
                )
            } catch (e: Exception) {
                Log.e("GameList", "Unexpected error", e)
            } finally {
                binding.swiperefresh.isRefreshing = false
            }
        }
    }


}