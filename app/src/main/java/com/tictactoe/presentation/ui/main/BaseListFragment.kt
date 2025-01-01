package com.tictactoe.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe.databinding.FragmentGameBinding
import com.tictactoe.domain.repository.GameRepository
import com.tictactoe.domain.viewModel.GameViewModel
import com.tictactoe.presentation.ui.main.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseListFragment : Fragment() {
    @Inject
    lateinit var gameRepository: GameRepository
    lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding
    private lateinit var gameAdapter: GameAdapter
    private val games = mutableListOf<Game>()

    abstract fun getViewModelMethod(): (GameViewModel) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel = GameViewModel(gameRepository)
        getViewModelMethod()(viewModel)
        gameAdapter = GameAdapter(requireContext(), games, activity as GameAdapter.OnGameClickListener)
        binding.recyclerView.adapter = gameAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.swiperefresh.setOnRefreshListener {
            getViewModelMethod()(viewModel)
        }

        viewModel.games.observe(viewLifecycleOwner) { newGames ->
            gameAdapter.updateGames(newGames)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swiperefresh.isRefreshing = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner, { errorMessage ->

        })

//        val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
//            override fun getMovementFlags(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ): Int {
//                // Разрешаем только свайпы вправо (удаление)
//                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//                return makeMovementFlags(0, swipeFlags)
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                // Когда элемент свайпается, удаляем его
//                val position = viewHolder.adapterPosition
//                if (direction == ItemTouchHelper.RIGHT) {
//                    // Удаляем игру с анимацией
//                    gameAdapter.removeGame(position)
//                    // Здесь можно добавить логику для удаления с серверной стороны, если нужно
//                }
//            }
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean = false
//
//            override fun isLongPressDragEnabled(): Boolean = false
//        }
//
//        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(binding.recyclerView)



        return binding.root
    }
}
