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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Offline_gamesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MinesweeperGame(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MinesweeperGame(modifier: Modifier = Modifier) {
    val rows = 10
    val cols = 10
    val mines = 15
    
    // boardVersion increments every time we want the UI to refresh
    var boardVersion by remember { mutableIntStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }

    LaunchedEffect(boardVersion == 0) {
        if (boardVersion == 0) {
            MinesweeperEngine.initGame(rows, cols, mines)
            gameOver = false
        }
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

        if (gameOver) {
            Text(
                text = "GAME OVER!",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            MinesweeperGrid(rows, cols, boardVersion, onCellClick = { r, c ->
                if (!gameOver) {
                    MinesweeperEngine.openCell(r, c)
                    gameOver = MinesweeperEngine.isGameOver()
                    boardVersion++ // Force recomposition of all cells
                }
            })
        }

        Button(
            onClick = { 
                boardVersion = 0 
                // We'll reset it to 0 and the LaunchedEffect will re-init
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Reset Game")
        }
    }
}

@Composable
fun MinesweeperGrid(rows: Int, cols: Int, version: Int, onCellClick: (Int, Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cols),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(rows * cols) { index ->
            val r = index / cols
            val c = index % cols
            
            // We use the 'version' key to make sure this re-calculates 
            // every time boardVersion changes.
            val status = remember(version) { MinesweeperEngine.getCellStatus(r, c) }

            MinesweeperCell(status = status) {
                onCellClick(r, c)
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
