package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by breinhold on 20.12.17.
 */

public class VictoryScreen extends AppCompatActivity {

    SharedPreferences spScore;
    int score;

    Button btnPlayAgain;
    Button btnBackToMainMenu;
    Button btnToScoreBoard;
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory_screen);

        spScore = getSharedPreferences("currentScore", MODE_PRIVATE);
        score = spScore.getInt("score", 0);

        btnPlayAgain = findViewById(R.id.btn_playagain);
        btnBackToMainMenu = findViewById(R.id.btn_vicScreen_to_menu);
        btnToScoreBoard = findViewById(R.id.btn_vicScreen_to_scoreboard);
        tvScore = findViewById(R.id.txtView_currentscore2);

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

        showScore();
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

    private void showScore()
    {
        String newScore = "";
        if (score >= 10000)
        {
            newScore = Integer.toString(score);
        }
        else if (score >= 1000)
        {
            newScore = "0" + Integer.toString(score);
        }
        else if (score >= 100)
        {
            newScore = "00" + Integer.toString(score);
        }
        else if (score >= 10)
        {
            newScore = "000" + Integer.toString(score);
        }
        tvScore.setText(newScore);
    }

}