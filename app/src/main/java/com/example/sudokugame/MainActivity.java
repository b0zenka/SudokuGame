package com.example.sudokugame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final int SIZE_OF_BOARD = 9;
    Random random = new Random();
    SudokuCell[][] sudokuCells;
    SudokuBoard sudokuBoard;
    byte[][] hidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuCells = new SudokuCell[SIZE_OF_BOARD][SIZE_OF_BOARD];

        setNewSudokuGame();
    }

    public void onClickNumberButton(View view) {
        if (SudokuCell.selectedCell == null)
            return;

        String strNumber = (String) ((Button) view).getText();
        SudokuCell.selectedCell.setText(strNumber);
    }

    public void startNewGame(View view) {
        setNewSudokuGame();
    }

    public void showHint(View view) {
        Map<Integer, Integer> keys = new HashMap<Integer, Integer>();
        boolean isFill = isFilled();
        byte value;

        for (int i = 0; i < SIZE_OF_BOARD; i++) {
            for (int j = 0; j < SIZE_OF_BOARD; j++) {
                value = sudokuCells[i][j].getValue();
                if (value == 0 || (isFill && value != sudokuBoard.getValueFromBoard(i, j))) {
                    keys.put(i, j);
                }
            }
        }

        if (keys.isEmpty()) {
            Toast.makeText(this, "Sudoku uzupełnione - sprawdź poprawność!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer x = random.nextInt(SIZE_OF_BOARD);
        while (!keys.containsKey(x)) {
            x = random.nextInt(SIZE_OF_BOARD);
        }

        int y = keys.get(x);
        sudokuCells[x][y].setValue(sudokuBoard.getValueFromBoard(x, y));
    }

    public void checkSudoku(View view) {
        if (!isFilled()) {
            Toast.makeText(this, "Uzupełnij wszystkie pola!!!",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isCorrect = true;
        for (int i = 0; i < SIZE_OF_BOARD; i++) {
            for (int j = 0; j < SIZE_OF_BOARD; j++) {
                if (sudokuCells[i][j].getValue() != sudokuBoard.getValueFromBoard(i, j)) {
                    isCorrect = false;
                    break;
                }
            }

            if(!isCorrect)
                break;
        }

        if (isCorrect) {
            Toast.makeText(this, "Gratulacje!!!\nMożesz rozpocząć nową grę!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sudoku nie jest wypełnione poprawnie!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFilled() {
        for (int i = 0; i < SIZE_OF_BOARD; i++) {
            for (int j = 0; j < SIZE_OF_BOARD; j++) {
                if (sudokuCells[i][j].getValue() == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private void setNewSudokuGame() {
        SudokuCell.selectedCell = null;
        int numberOfHiddenCells = random.nextInt(21) + 40;
        sudokuBoard = new SudokuBoard(numberOfHiddenCells);
        hidden = sudokuBoard.getBoardHidden();

        String name;
        int id;
        Button btn;

        for (int i = 0; i < SIZE_OF_BOARD; i++) {
            for (int j = 0; j < SIZE_OF_BOARD; j++) {
                name = "button" + i + j;
                id = getResources().getIdentifier(name, "id", getPackageName());
                btn = findViewById(id);
                sudokuCells[i][j] = new SudokuCell(hidden[i][j], btn);
            }
        }
    }
}