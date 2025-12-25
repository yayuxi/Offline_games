#include <jni.h>
#include "minesweeper.h"

static struct Board currentBoard;
static bool initialized = false;

JNIEXPORT void JNICALL
Java_com_example_offline_1games_MinesweeperEngine_initGame(JNIEnv *env, jobject thiz, jint rows, jint cols, jint mines) {
    if (initialized) {
        freeBoard(&currentBoard);
    }
    currentBoard.rows = rows;
    currentBoard.cols = cols;
    currentBoard.nrOfMines = mines;
    start(&currentBoard);
    initialized = true;
}

JNIEXPORT void JNICALL
Java_com_example_offline_1games_MinesweeperEngine_openCell(JNIEnv *env, jobject thiz, jint row, jint col) {
    if (initialized) {
        open(&currentBoard, row, col);
    }
}

JNIEXPORT jint JNICALL
Java_com_example_offline_1games_MinesweeperEngine_getCellStatus(JNIEnv *env, jobject thiz, jint row, jint col) {
    if (!initialized) return -2;
    if (currentBoard.hidden[row][col]) return -2; // Hidden
    if (currentBoard.mine[row][col]) return -1;   // Mine
    return currentBoard.neighborMines[row][col];  // Number of mines
}

JNIEXPORT jboolean JNICALL
Java_com_example_offline_1games_MinesweeperEngine_isGameOver(JNIEnv *env, jobject thiz) {
    return initialized && currentBoard.dead;
}
