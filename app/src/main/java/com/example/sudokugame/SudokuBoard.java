package com.example.sudokugame;

import java.util.Random;

public class SudokuBoard {
    private final byte N = 3;
    private final byte SHUFFLE_COUNT = 40;
    private Random random;
    private byte[][] board;
    private byte[][] boardHidden;

    public byte[][] getBoard() {
        return board;
    }

    public byte[][] getBoardHidden() {
        return boardHidden;
    }

    public byte getValueFromBoardHidden(int x, int y) {
        return  boardHidden[x][y];
    }

    public byte getValueFromBoard(int x, int y) {
        return  board[x][y];
    }

    public SudokuBoard(int cellsToHide) {
        random = new Random();
        generateMap();
        hideCells(cellsToHide);
    }

    public void generateMap() {
        byte size = N * N;
        board = new byte[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = (byte) ((i * N + i / N + j) % (size) + 1);
            }
        }

        for (int i = 0; i < SHUFFLE_COUNT; i++) {
            shuffleBoard((byte)random.nextInt(5));
        }

        copyToHidden();
    }

    private void copyToHidden() {
        int size = N * N;
        boardHidden = new byte[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardHidden[i][j] = board[i][j];
            }
        }
    }

    private void hideCells(int cellsToHide) {
        byte size = N * N;

        while (cellsToHide > 0) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (boardHidden[i][j] != 0) {
                        byte a = (byte)random.nextInt(N);

                        if (a == 0) {
                            cellsToHide--;
                            boardHidden[i][j] = 0;
                        }

                        if (cellsToHide <= 0)
                            break;
                    }
                }

                if (cellsToHide <= 0)
                    break;
            }
        }
    }

    private void shuffleBoard(byte shuffle) {
        switch (shuffle) {
            case ShuffleType.MatrixTransposition:
                matrixTransposition();
                break;
            case ShuffleType.SwapRowsInBlock:
                swapRowsInBlock();
                break;
            case ShuffleType.SwapColumnsInBlock:
                swapColumnsInBlock();
                break;
            case ShuffleType.SwapBlocksInRow:
                swapBlocksInRow();
                break;
            case ShuffleType.SwapBlocksInColumn:
                swapBlocksInColumn();
                break;
            default:
                break;
        }
    }

    private void swapBlocksInColumn() {
        int block1 = random.nextInt(N);
        int block2 = random.nextInt(N);
        while (block1 == block2)
            block2 = random.nextInt(N);

        block1 *= N;
        block2 *= N;
        for (int i = 0; i < N * N; i++) {
            int k = block2;
            for (int j = block1; j < block1 + N; j++) {
                byte temp = board[i][j];
                board[i][j] = board[i][k];
                board[i][k] = temp;
                k++;
            }
        }
    }

    private void swapBlocksInRow() {
        int block1 = random.nextInt(N);
        int block2 = random.nextInt(N);
        while (block1 == block2)
            block2 = random.nextInt(N);

        block1 *= N;
        block2 *= N;
        for (int i = 0; i < N * N; i++) {
            int k = block2;
            for (int j = block1; j < block1 + N; j++) {
                byte temp = board[j][i];
                board[j][i] = board[k][i];
                board[k][i] = temp;
                k++;
            }
        }
    }

    private void swapColumnsInBlock() {
        int block = random.nextInt(N);
        int row1 = random.nextInt(N);
        int line1 = block * N + row1;
        int row2 = random.nextInt(N);
        while (row1 == row2)
            row2 = random.nextInt(N);
        int line2 = block * N + row2;
        for (int i = 0; i < N * N; i++) {
            byte temp = board[i][line1];
            board[i][line1] = board[i][line2];
            board[i][line2] = temp;
        }
    }

    private void swapRowsInBlock() {
        int block = random.nextInt(N);
        int row1 = random.nextInt(N);
        int line1 = block * N + row1;
        int row2 = random.nextInt(N);
        while (row1 == row2)
            row2 = random.nextInt(N);
        int line2 = block * N + row2;
        for (int i = 0; i < N * N; i++) {
            byte temp = board[line1][i];
            board[line1][i] = board[line2][i];
            board[line2][i] = temp;
        }
    }

    private void matrixTransposition() {
        byte[][] tboard = new byte[N * N][N * N];
        for (int i = 0; i < N * N; i++) {
            for (int j = 0; j < N * N; j++) {
                tboard[i][j] = board[j][i];
            }
        }
        board = tboard;
    }
}

