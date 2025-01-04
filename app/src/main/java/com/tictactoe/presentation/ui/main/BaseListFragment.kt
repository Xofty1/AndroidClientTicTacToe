package com.tictactoe.presentation.ui.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
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
        val fragmentGameClickListener = if (this is GameAdapter.OnFragmentGameClickListener) {
            this
        } else {
            null
        }
        gameAdapter = GameAdapter(requireContext(), games, activity as GameAdapter.OnGameClickListener, fragmentGameClickListener)
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

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            private val paintBackground = Paint().apply {
                color = Color.RED
                isAntiAlias = true
            }

            private val paintText = Paint().apply {
                color = Color.BLACK
                textSize = 48f
                isAntiAlias = true
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Рисуем красный фон
                    val background = RectF(
                        itemView.right + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paintBackground)

                    // Рисуем текст "Удалить", привязанный к текущей границе красной области
                    val text = "Удалить"
                    val textWidth = paintText.measureText(text)
                    val textX = itemView.right + dX / 2
                    val textY = itemView.top + (itemView.height + paintText.textSize) / 2
                    canvas.drawText(text, textX, textY, paintText)
                }

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val swipeFlags = ItemTouchHelper.LEFT
                return makeMovementFlags(0, swipeFlags)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.removeGame(gameAdapter.games[position].id.toString())
                    gameAdapter.removeGame(position)
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun isLongPressDragEnabled(): Boolean = false
        }




        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)



        return binding.root
    }
}
