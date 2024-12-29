package com.tictactoe.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.tictactoe.R
import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.datasource.room.DatabaseService
import com.tictactoe.datasource.room.factory.NewGameFactory
import dagger.hilt.android.AndroidEntryPoint
import domain.model.Game
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class CreateGameFragment : Fragment() {

    @Inject
    lateinit var networkService: NetworkService

    @Inject
    lateinit var databaseService: DatabaseService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ссылки на элементы
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupOpponent)
        val btnCreateGame = view.findViewById<Button>(R.id.btnCreateGame)

        btnCreateGame.setOnClickListener {
            // Определяем выбранный вариант
            val selectedOption = when (radioGroup.checkedRadioButtonId) {
                R.id.rbComputer -> "Компьютер"
                R.id.rbPlayer -> "Другой игрок"
                else -> null
            }

            if (selectedOption != null) {
                // Логика создания игры
                lifecycleScope.launch {
                    try {
                        // Запрос к API
                        val gameDataResult = networkService.createNewGame(
                            "X",
                            databaseService.getCurrentUser().login,
                            null // надо будет добавить Компьютер в качестве оппонента
                        )

                        gameDataResult.onSuccess { gameData ->
                            // Извлекаем первый элемент из Map
                            val (gameId, gameDto) = gameData.entries.first()

                            // Создаем объект Game
                            val game = NewGameFactory.createNewGame(
                                gameId,
                                gameDto.firstUserLogin,
                                gameDto.secondUserLogin
                            )

                            // Переход на новый фрагмент
                            (activity as? MainActivity)?.let { mainActivity ->
                                val gameFragment = TicTacToeFragment(game)
                                mainActivity.supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, gameFragment)
                                    .addToBackStack(null)
                                    .commit()
                            }

                            Toast.makeText(
                                requireContext(),
                                "Игра с ${game.secondUserLogin ?: "Компьютером"} создана!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.onFailure { error ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка: ${error.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                Toast.makeText(requireContext(), "Выберите оппонента!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(): CreateGameFragment {
            return CreateGameFragment()
        }
    }
}