package com.example.offline_games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.offline_games.ui.theme.Offline_gamesTheme


enum class Screen {
    MENU,
    CUSTOM,
    GAME
}

enum class Difficulty(
    val rows: Int,
    val cols: Int,
    val mines: Int
) {
    EASY(8, 8, 10),
    MEDIUM(10, 10, 15),
    HARD(16, 16, 40),

    CUSTOM(0, 0, 0)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var customRows by remember { mutableIntStateOf(10) }
            var customCols by remember { mutableIntStateOf(10) }
            var customMines by remember { mutableIntStateOf(15) }

            Offline_gamesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentScreen by remember {
                        mutableStateOf(Screen.MENU)
                    }

                    var selectedDifficulty by remember {
                        mutableStateOf(Difficulty.MEDIUM)
                    }

                    when (currentScreen) {

                        Screen.CUSTOM -> {

                            CustomGameScreen(
                                initialRows = customRows,
                                initialCols = customCols,
                                initialMines = customMines,

                                onStartGame = { rows, cols, mines ->

                                    customRows = rows
                                    customCols = cols
                                    customMines = mines

                                    selectedDifficulty = Difficulty.CUSTOM

                                    currentScreen = Screen.GAME
                                },

                                onBack = {
                                    currentScreen = Screen.MENU
                                }
                            )
                        }

                        Screen.MENU -> {
                            MainMenu(
                                onDifficultySelected = { difficulty ->

                                    if (difficulty == Difficulty.CUSTOM) {
                                        currentScreen = Screen.CUSTOM
                                    } else {
                                        selectedDifficulty = difficulty
                                        currentScreen = Screen.GAME
                                    }
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        Screen.GAME -> {
                            MinesweeperGame(
                                difficulty = selectedDifficulty,

                                customRows = customRows,
                                customCols = customCols,
                                customMines = customMines,

                                onBackToMenu = {
                                    currentScreen = Screen.MENU
                                },

                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}