package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}  // Diagonals
    };

    private int[] gameState = new int[9];
    private int currentPlayer = 1; // 1 for Player X, 2 for Player O
    private boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetGame();
    }

    public void onCellClicked(View view) {
        if (!gameActive) {
            return;
        }

        Button clickedButton = (Button) view;
        int clickedTag = Integer.parseInt(clickedButton.getTag().toString());

        if (gameState[clickedTag] != 0) {
            return; // Cell already occupied
        }

        gameState[clickedTag] = currentPlayer;
        clickedButton.setText(currentPlayer == 1 ? "X" : "O");

        if (checkWinner()) {
            gameActive = false;
            String winner = currentPlayer == 1 ? "Player X" : "Player O";
            Toast.makeText(this, winner + " has won!", Toast.LENGTH_LONG).show();
        } else if (isDraw()) {
            gameActive = false;
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
        } else {
            currentPlayer = currentPlayer == 1 ? 2 : 1; // Switch player
        }
    }

    private boolean checkWinner() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int cellState : gameState) {
            if (cellState == 0) {
                return false;
            }
        }
        return true;
    }

    public void resetGame(View view) {
        resetGame();
    }

    private void resetGame() {
        gameState = new int[9];
        currentPlayer = 1;
        gameActive = true;

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((Button) gridLayout.getChildAt(i)).setText("");
        }
    }
}
