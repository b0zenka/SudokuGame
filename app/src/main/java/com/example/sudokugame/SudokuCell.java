package com.example.sudokugame;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class SudokuCell {
    public static Button selectedCell;
    private boolean isFixed;
    private Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public byte getValue() {
        String text = (String) button.getText();
        if (text == "")
            return 0;
        return Byte.parseByte(text);
    }

    public void setValue(byte value) {
        resetSelectedCell();

        this.button.setText(String.valueOf(value));
        this.button.setBackgroundColor(Color.parseColor("#C461B3"));
        selectedCell = this.button;
    }

    public SudokuCell(byte value, Button button) {
        if (value != 0)
            this.isFixed = true;
        else
            this.isFixed = false;

        this.button = button;

        if (this.isFixed) {
            this.button.setText(String.valueOf(value));
            this.button.setBackgroundColor(Color.parseColor("#EFE9E7"));
        } else {
            this.button.setText("");
            this.button.setBackgroundColor(Color.WHITE);
        }

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFixed)
                    return;

                resetSelectedCell();

                selectedCell = (Button) view;
                button.setBackgroundColor(Color.parseColor("#F9CFF2"));
            }
        });
    }

    private static void resetSelectedCell() {
        if (selectedCell != null)
            selectedCell.setBackgroundColor(Color.WHITE);
    }
}
