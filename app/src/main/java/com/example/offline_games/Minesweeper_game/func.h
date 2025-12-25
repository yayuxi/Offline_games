//
// Created by Bruger 1 on 15-10-2024.
//
#include "struct.h"


#ifndef MINESWEEPER_FUNC_H
#define MINESWEEPER_FUNC_H
void start(struct Board* board);
void open(struct Board* board, int row, int col);
void nrMine(struct Board* board, int row, int col);
void isFreeSpace(struct Board* board);
void printBoard(struct Board* board);
void GameOver(struct Board* board, int row, int col);
void freeBoard(struct Board* board);


#endif //MINESWEEPER_FUNC_H
