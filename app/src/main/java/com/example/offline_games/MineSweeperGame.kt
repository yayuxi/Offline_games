package com.example.offline_games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MinesweeperGame(
    difficulty: Difficulty,
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = difficulty.rows
    val cols = difficulty.cols
    val mines = difficulty.mines

    // boardVersion increments every time we want the UI to refresh
    var resetTrigger by remember { mutableIntStateOf(0) }
    var boardVersion by remember { mutableIntStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var hasWon by remember { mutableStateOf(false) }

    LaunchedEffect(resetTrigger) {
        MinesweeperEngine.initGame(rows, cols, mines)
        gameOver = false
        hasWon = false
        boardVersion++
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Minesweeper",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = onBackToMenu,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Back to Menu")
        }

        if (gameOver) {
            Text(
                text = "GAME OVER!",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        } else if (hasWon) {
            Text(
                text = "YOU WIN!",
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            key(rows, cols) {
                MinesweeperGrid(rows, cols, boardVersion, onCellClick = { r, c ->
                    if (!gameOver && !hasWon) {
                        MinesweeperEngine.openCell(r, c)
                        gameOver = MinesweeperEngine.isGameOver()
                        hasWon = MinesweeperEngine.hasWon()
                        boardVersion++
                    }
                })
            }
        }

        Button(
            onClick = {
                resetTrigger++ // Trigger a new game
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Reset Game")
        }
    }
}

@Composable
fun MinesweeperGrid(rows: Int, cols: Int, version: Int, onCellClick: (Int, Int) -> Unit) {
    val spacing = if (cols > 10) 2.dp else 4.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed(cols),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(cols.toFloat() / rows.toFloat()),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(rows * cols) { index ->
            val r = index / cols
            val c = index % cols

            // We use the 'version' key to make sure this re-calculates
            // every time boardVersion changes.
            key(version) {
                val status = MinesweeperEngine.getCellStatus(r, c)

                MinesweeperCell(status = status) {
                    onCellClick(r, c)
                }
            }
        }
    }
}

@Composable
fun MinesweeperCell(status: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(if (status == -2) Color.Gray else Color.LightGray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when (status) {
            -1 -> Text("💣", fontSize = 18.sp)
            -2 -> { /* Hidden */ }
            0 -> { /* Empty space opened */ }
            else -> Text(
                text = status.toString(),
                fontWeight = FontWeight.Bold,
                color = when(status) {
                    1 -> Color.Blue
                    2 -> Color(0xFF388E3C)
                    3 -> Color.Red
                    else -> Color.Magenta
                }
            )
        }
    }
}
