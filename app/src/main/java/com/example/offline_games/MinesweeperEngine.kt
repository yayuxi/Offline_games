package com.example.offline_games

object MinesweeperEngine {
    init {
        System.loadLibrary("offline_games")
    }

    external fun initGame(rows: Int, cols: Int, mines: Int)
    external fun openCell(row: Int, col: Int)
    external fun toggleFlag(row: Int, col: Int)
    external fun getCellStatus(row: Int, col: Int): Int
    external fun isGameOver(): Boolean
    external fun hasWon(): Boolean
}
