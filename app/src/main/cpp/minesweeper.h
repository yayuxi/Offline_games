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
    int input[2];
    int **neighborMines;
    bool dead;
};

void start(struct Board* board);
void open(struct Board* board, int row, int col);
void nrMine(struct Board* board, int row, int col);
void freeBoard(struct Board* board);

#endif
