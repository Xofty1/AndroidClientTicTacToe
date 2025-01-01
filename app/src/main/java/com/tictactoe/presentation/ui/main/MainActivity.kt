package com.tictactoe.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tictactoe.R
import com.tictactoe.databinding.ActivityMainBinding
import com.tictactoe.domain.repository.GameRepository
import com.tictactoe.domain.viewModel.GameViewModel
import com.tictactoe.presentation.ui.authorization.AuthorizationActivity
import com.tictactoe.presentation.ui.main.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), GameAdapter.OnGameClickListener {
    lateinit var viewModel: GameViewModel
    @Inject
    lateinit var gameRepository: GameRepository

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = GameViewModel(gameRepository)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // Добавьте код для обработки нажатия на кнопку Logout
                Toast.makeText(this, "Logout clicked!", Toast.LENGTH_SHORT).show()

                // Например, удалите данные пользователя и перейдите на экран входа
                handleLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogout() {
        viewModel.clearData()


        val intent = Intent(this, AuthorizationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
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

