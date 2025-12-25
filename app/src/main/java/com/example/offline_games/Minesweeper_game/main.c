#include <stdio.h>
#include "func.h"

int main(void) {
    struct Board board;
    printf("Enter two numbers which will be the number of rows and coloumns.\n");
    scanf("%d %d", &board.rows, &board.cols);
    printf("Now enter the number of mines.\n");
    scanf("%d", &board.nrOfMines);
    start(&board);
    printBoard(&board);

    int input;
    while (!board.dead) {
        printf("\nEnter a two-digit number: ");
        if (scanf("%d", &input) != 1 || input < 0 || input > (board.rows*board.cols)-1) {
            printf("Invalid input. Please enter a two-digit number between 0 and 99.\n");
            // Clear the input buffer in case of invalid input
            while (getchar() != '\n');
            continue;
        }

        int row = input / board.rows; // Extract the first digit as the row
        int col = input % board.cols; // Extract the second digit as the column

        open(&board, row, col);
        printBoard(&board);

        if (board.dead) {
            printf("\nGame Over! You hit a mine.\n");
            freeBoard(&board);
            break;
        }
    }
    return 0;
}
