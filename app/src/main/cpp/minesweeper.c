#include "minesweeper.h"
#include <time.h>

void allocateBoard(struct Board* board) {
    board->mine = malloc(board->rows * sizeof(bool*));
    board->hidden = malloc(board->rows * sizeof(bool*));
    board->neighborMines = malloc(board->rows * sizeof(int*));

    for (int i = 0; i < board->rows; i++) {
        board->mine[i] = malloc(board->cols * sizeof(bool));
        board->hidden[i] = malloc(board->cols * sizeof(bool));
        board->neighborMines[i] = malloc(board->cols * sizeof(int));
    }
}

void nrMine(struct Board* board, int row, int col) {
    board->neighborMines[row][col] = 0;
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) continue;
            int neighborRow = row + i;
            int neighborCol = col + j;
            if (neighborRow >= 0 && neighborRow < board->rows &&
                neighborCol >= 0 && neighborCol < board->cols) {
                if (board->mine[neighborRow][neighborCol]) {
                    board->neighborMines[row][col]++;
                }
            }
        }
    }
}

void start(struct Board* board) {
    allocateBoard(board);
    board->dead = false;
    for (int i = 0; i < board->rows; i++) {
        for (int j = 0; j < board->cols; j++) {
            board->mine[i][j] = false;
            board->hidden[i][j] = true;
        }
    }

    board->firstMove = true;
}

void placeMines(struct Board* board, int safeRow, int safeCol) {

    srand(time(NULL));

    int minesPlaced = 0;

    while (minesPlaced < board->nrOfMines) {

        int r = rand() % board->rows;
        int c = rand() % board->cols;

        // Skip if already a mine
        if (board->mine[r][c]) {
            continue;
        }

        // Skip the clicked cell and surrounding cells
        if (r >= safeRow - 1 && r <= safeRow + 1 &&
            c >= safeCol - 1 && c <= safeCol + 1) {
            continue;
        }

        board->mine[r][c] = true;
        minesPlaced++;
    }

    // Calculate neighbor counts
    for (int i = 0; i < board->rows; i++) {
        for (int j = 0; j < board->cols; j++) {
            nrMine(board, i, j);
        }
    }
}

void open(struct Board* board, int row, int col) {

    if (row < 0 || row >= board->rows || col < 0 || col >= board->cols) return;
    if (!board->hidden[row][col]) return;

    if (board->firstMove) {
        placeMines(board, row, col);
        board->firstMove = false;
    }

    if (board->mine[row][col]) {
        board->dead = true;
        revealAll(board);
        return;
    }

    board->hidden[row][col] = false;

    if (board->neighborMines[row][col] == 0) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    open(board, row + i, col + j);
                }
            }
        }
    }
}

bool hasWon(struct Board* board) {

    // You cannot win if you're dead
    if (board->dead) {
        return false;
    }

    for (int i = 0; i < board->rows; i++) {
        for (int j = 0; j < board->cols; j++) {

            // Any hidden non-mine means game continues
            if (!board->mine[i][j] && board->hidden[i][j]) {
                return false;
            }
        }
    }

    return true;
}

void revealAll(struct Board* board) {
    for (int i = 0; i < board->rows; i++) {
        for (int j = 0; j < board->cols; j++) {
            board->hidden[i][j] = false;
        }
    }
}

void freeBoard(struct Board* board) {
    for (int i = 0; i < board->rows; i++) {
        free(board->mine[i]);
        free(board->hidden[i]);
        free(board->neighborMines[i]);
    }
    free(board->mine);
    free(board->hidden);
    free(board->neighborMines);
}
