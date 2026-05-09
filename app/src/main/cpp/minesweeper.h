#ifndef MINESWEEPER_H
#define MINESWEEPER_H

#include <stdbool.h>
#include <stdlib.h>

struct Board {
    int nrOfMines;
    int rows;
    int cols;
    bool **mine;
    bool **hidden;
    bool firstMove;
    int input[2];
    int **neighborMines;
    bool dead;
};

void start(struct Board* board);
void placeMines(struct Board* board, int safeRow, int safeCol);
void open(struct Board* board, int row, int col);
void nrMine(struct Board* board, int row, int col);
bool hasWon(struct Board* board);
void revealAll(struct Board* board);
void freeBoard(struct Board* board);

#endif
