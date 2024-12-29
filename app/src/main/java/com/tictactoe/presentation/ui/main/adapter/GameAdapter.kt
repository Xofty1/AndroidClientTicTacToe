package com.tictactoe.presentation.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tictactoe.R
import domain.model.Game


class GameAdapter(
    private val context: Context,
    var games: List<Game>,
    private val listener: OnGameClickListener
) : ArrayAdapter<Game>(context, 0, games) {
    interface OnGameClickListener {
        fun onGameClicked(game: Game)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)

        val game = getItem(position)

        val tvGameTitle = view.findViewById<TextView>(R.id.tvGameTitle)
        val tvGameDescription = view.findViewById<TextView>(R.id.tvGameDescription)

        tvGameTitle.text = (game?.id ?: "Unknown Game").toString()
        tvGameDescription.text = (game?.firstUserLogin ?: "No Description").toString()
        view.setOnClickListener {
            game?.let { listener.onGameClicked(it) }
        }
        return view
    }

    fun updateGames(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}
