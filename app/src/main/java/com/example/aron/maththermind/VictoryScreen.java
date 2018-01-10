package com.example.aron.maththermind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by breinhold on 20.12.17.
 */

public class VictoryScreen extends AppCompatActivity {

    Button btnPlayAgain;
    Button btnBackToMainMenu;
    Button btnToScoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory_screen);

        btnPlayAgain = findViewById(R.id.btn_playagain);
        btnBackToMainMenu = findViewById(R.id.btn_vicScreen_to_menu);
        btnToScoreBoard = findViewById(R.id.btn_vicScreen_to_scoreboard);

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameScreenActivity();
            }
        });

        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnToScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScoreBoardActivity();
            }
        });
    }

    private void startGameScreenActivity()
    {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
        finish();
    }

    private void startScoreBoardActivity()
    {
        Intent intent = new Intent(this, ScoreBoard.class);
        startActivity(intent);
        finish();
    }

}