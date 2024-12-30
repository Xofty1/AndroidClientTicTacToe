package com.tictactoe.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tictactoe.R
import com.tictactoe.databinding.ActivityMainBinding
import com.tictactoe.presentation.ui.main.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), GameAdapter.OnGameClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GameFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when (item.itemId) {
                R.id.nav_create_game -> {
                    if (currentFragment !is GameFragment) {
                        switchFragment(GameFragment())
                    }
                    true
                }

                R.id.nav_profile -> {
                    if (currentFragment !is CreateGameFragment) {
                        switchFragment(CreateGameFragment())
                    }
                    true
                }
                R.id.nav_find_game -> {
                    if (currentFragment !is JoinGamesFragment) {
                        switchFragment(JoinGamesFragment())
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onGameClicked(game: Game) {
        val newFragment = TicTacToeFragment(game)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


}

