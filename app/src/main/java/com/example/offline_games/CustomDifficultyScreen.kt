package com.example.offline_games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun CustomGameScreen(
    initialRows: Int,
    initialCols: Int,
    initialMines: Int,

    onStartGame: (Int, Int, Int) -> Unit,
    onBack: () -> Unit
) {

    var rows by remember {
        mutableStateOf(initialRows.toString())
    }

    var cols by remember {
        mutableStateOf(initialCols.toString())
    }

    var mines by remember {
        mutableStateOf(initialMines.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Custom Game",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = rows,
            onValueChange = { rows = it },
            label = { Text("Rows") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = cols,
            onValueChange = { cols = it },
            label = { Text("Columns") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = mines,
            onValueChange = { mines = it },
            label = { Text("Mines") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val r = rows.toIntOrNull() ?: return@Button
                val c = cols.toIntOrNull() ?: return@Button
                val m = mines.toIntOrNull() ?: return@Button

                // Validation
                if (r < 2 || c < 2)
                    return@Button

                if (m <= 0 || m >= r * c)
                    return@Button

                onStartGame(r, c, m)
            }
        ) {
            Text("Start Game")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }
    }
}