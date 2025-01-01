package com.tictactoe.presentation.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe.R
import domain.model.Game


class GameAdapter(
    private val context: Context,
    var games: MutableList<Game>,  // Используем MutableList для возможности модификации
    private val listener: OnGameClickListener,
    private val fragmentListener: OnFragmentGameClickListener? = null
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    interface OnGameClickListener {
        fun onGameClicked(game: Game)
    }

    interface OnFragmentGameClickListener {
        fun onFragmentGameClicked(game: Game)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        Log.d("GameAdapter", "Binding game: ${view}")
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        Log.d("GameAdapter", "Binding game: ${game.id}")
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGames(newGames: List<Game>) {
        Log.d("GameAdapter", "Updating games, new size: ${games.size}")

        games.clear()
        Log.d("GameAdapter", "Updating games, new size: ${games.size}")
        games.addAll(newGames)
        Log.d("GameAdapter", "Updating games, new size: ${games.size}")
        notifyDataSetChanged()
    }

    fun removeGame(position: Int) {
        games.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvGameTitle: TextView = view.findViewById(R.id.tvGameTitle)
        private val tvGameDescription: TextView = view.findViewById(R.id.tvGameDescription)

        fun bind(game: Game) {
            tvGameTitle.text = ("ID: " + game.id)
            tvGameDescription.text = ("Логин создателя: " + game.firstUserLogin)

            itemView.setOnClickListener {
                listener.onGameClicked(game)
                fragmentListener?.onFragmentGameClicked(game)
            }
        }
    }
}
