cmake_minimum_required(VERSION 3.22.1)

project("offline_games")

add_library(
    ${PROJECT_NAME}
    SHARED
    minesweeper.c
    native-lib.c
)

find_library(
    log-lib
    log
)

target_link_libraries(
    ${PROJECT_NAME}
    ${log-lib}
)
