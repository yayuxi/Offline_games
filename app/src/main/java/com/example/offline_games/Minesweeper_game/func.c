//
// Created by Bruger 1 on 15-10-2024.
//
#include <assert.h>
#include "func.h"

void allocateBoard(struct Board* board) {
    // Allocate each 2D array
    board->mine = malloc(board->rows * sizeof(bool*));
    board->hidden = malloc(board->rows * sizeof(bool*));
    board->neighborMines = malloc(board->rows * sizeof(int*));

    for (int i = 0; i < board->rows; i++) {
        board->mine[i] = malloc(board->cols * sizeof(bool));
        board->hidden[i] = malloc(board->cols * sizeof(bool));
        board->neighborMines[i] = malloc(board->cols * sizeof(int));
    }
}

void start(struct Board* board){
    allocateBoard(board);
    board->dead = false;
    for (int i=0; i<board->rows; i++){
        for (int j=0; j<board->cols; j++){
            board->mine[i][j] = false;
            board->hidden[i][j] = true;
        }
    }
    for (int i=0; i<board->nrOfMines; i++){
        int n = rand() % board->rows;
        int m = rand() % board->cols;
        board->mine[n][m] = true;
    }
    for (int i=0; i<board->rows; i++){
        for (int j=0; j<board->cols; j++){
            nrMine(board, i, j);
        }
    }
    assert(!board->dead && "Game should not start in a dead state!");
}

void open(struct Board* board, int row, int col) {
    // Ensure the row and column are within bounds (0 to 9)
    if (row < 0 || row >= board->rows || col < 0 || col >= board->cols) {
        return; // Out of bounds
    }

    // If the cell is already revealed, do nothing
    if (!board->hidden[row][col]) {
        return;
    }

    // If the cell contains a mine, set the game to dead state and return
    if (board->mine[row][col]) {
        board->hidden[row][col] = false; // Reveal the mine
        board->dead = true;             // End the game
        return;
    }

    // Reveal the current cell
    board->hidden[row][col] = false;

    // If the cell has no neighboring mines, open neighbors recursively
    if (board->neighborMines[row][col] == 0) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) { // Skip the current cell
                    int neighborRow = row + i;
                    int neighborCol = col + j;

                    if (neighborRow >= 0 && neighborRow < board->rows &&
                        neighborCol >= 0 && neighborCol < board->cols) {
                        open(board, neighborRow, neighborCol);
                    }
                }
            }
        }
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



void isFreeSpace(struct Board* board){


}

void printBoard(struct Board* board){
    printf("\n\n");
    for (int i = 0; i < board->rows; i++) {
        for (int j = 0; j < board->cols; j++) {
            if (board->hidden[i][j]) {
                printf("[+]");
            } else if (board->mine[i][j]) {
                printf(" * ");
            } else {
                printf(" %d ", board->neighborMines[i][j]);
            }
        }
        printf("\n");
    }
}


void GameOver(struct Board* board, int row, int col){
    if (board->mine[row][col]){
        printBoard(board);
        printf("%s", "\nGame Over!");
        exit(0);
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
