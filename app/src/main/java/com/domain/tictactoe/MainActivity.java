package com.domain.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.tictactoe.domain.Player;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private Player player1 = new Player("player1");
    private Player player2 = new Player("player2");
    private boolean player1Turn = true;
    private int roundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.textViewPlayer1);
        textViewPlayer2 = findViewById(R.id.textViewPlayer2);

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                String buttonId = "button" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
//        if (!((Button) view).getText().toString().equals("")) {
//            return;
//        }

        if (((Button) view).getText().toString().equals("")) {
            if (player1Turn) {
                ((Button) view).setText("X");
            } else {
                ((Button) view).setText("O");
            }

            roundCount++;

            if (checkForWin()) {
                if (player1Turn) {
                    playerWins(player1);
                }
                else {
                    playerWins(player2);
                }
            }
            else if (roundCount >= 9) {
                draw();
            }
            else {
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean checkForWin() {
        String[][] fields = new String[buttons.length][buttons[0].length];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Rows
        for (int i = 0; i < buttons.length; i++) {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals("")) {
                return true;
            }
        }

        // Columns
        for (int i = 0; i < buttons[0].length; i++) {
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals("")) {
                return true;
            }
        }

        // Diagonals
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals("")) {
            return true;
        }

        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0]) && !fields[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void playerWins(Player player) {
        player.setScore(player.getScore() + 1);
        if (player.equals(player1)) {
            Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        }
        else if (player.equals(player2)) {
            Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Something went wrong :|", Toast.LENGTH_SHORT).show();
        }
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1.getScore());
        textViewPlayer2.setText("Player 2: " + player2.getScore());
    }

    private void resetBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1.setScore(0);
        player2.setScore(0);
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putParcelable("player1", player1);
        outState.putParcelable("player2", player2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        player1 = savedInstanceState.getParcelable("player1");
        player2 = savedInstanceState.getParcelable("player2");
    }
}
