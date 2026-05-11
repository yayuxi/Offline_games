#ifndef MINESWEEPER_H
#define MINESWEEPER_H

#include <stdbool.h>
#include <stdlib.h>

struct Board {
    int rows;
    int cols;
    int nrOfMines;

    bool **mine;
    bool **hidden;
    bool** flagged;

    int input[2];
    int **neighborMines;

    bool dead;
    bool firstMove;
};

void start(struct Board* board);
void placeMines(struct Board* board, int safeRow, int safeCol);
void open(struct Board* board, int row, int col);
void toggleFlag(struct Board* board, int row, int col);
void nrMine(struct Board* board, int row, int col);
bool hasWon(struct Board* board);
void revealAll(struct Board* board);
void freeBoard(struct Board* board);

#endif
