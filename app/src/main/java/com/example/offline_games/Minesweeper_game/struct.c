#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

//
// Created by Bruger 1 on 15-10-2024.
//
struct Board {
    int nrOfMines;
    int rows;
    int cols;
    bool **mine;
    bool **hidden;
    int input[2]; //going to be the variable that changes the board's size
    int **neighborMines;
    bool dead;
};