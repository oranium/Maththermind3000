package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by breinhold on 20.12.17.
 */

public class VictoryScreen extends BaseActivity {
    SharedPreferences spScore, spSound;
    int score, solved;
    int lives;
    boolean sfxOn;
    Button btnPlayAgain;
    Button btnBackToMainMenu;
    Button btnToScoreBoard;
    TextView tvScore;
    TextView tvNewHighscore;
    TextView tvSolved;
    ImageView ivLife1;
    ImageView ivLife2;
    ImageView ivLife3;
    Button btnShare;
    MusicThread musicThread;
    MediaPlayer mpApplause;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory_screen);

        spScore = getSharedPreferences("currentScore", MODE_PRIVATE);
        spSound = getSharedPreferences("sound",MODE_PRIVATE);
        score = spScore.getInt("score", 0);
        solved = spScore.getInt("solved", 0);
        lives = spScore.getInt("lives", 0);
        musicThread = MainScreen.musicThread;
        btnShare = findViewById(R.id.btn_share);
        btnPlayAgain = findViewById(R.id.btn_playagain);
        btnBackToMainMenu = findViewById(R.id.btn_vicScreen_to_menu);
        btnToScoreBoard = findViewById(R.id.btn_vicScreen_to_scoreboard);
        tvScore = findViewById(R.id.txtView_currentscore2);
        tvSolved = findViewById(R.id.txtView_solvedExercises);
        tvNewHighscore = findViewById(R.id.txtView_newHighscore);
        ivLife1 = findViewById(R.id.imageView_life);
        ivLife2 = findViewById(R.id.imageView_life5);
        ivLife3 = findViewById(R.id.imageView_life1);
        sfxOn = spSound.getBoolean("sfx",false);
        mpApplause = MediaPlayer.create(this, R.raw.applause);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareText = "I scored "+score+" points in Maththermind! Come play with me, loser.";
                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareText);
                startActivity(Intent.createChooser(sharingIntent, "Share via:"));
            }
        });

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

        // get some extra points if you have more than one life left
        if (lives == 3)
        {
            score += score / 5;
        }
        else if (lives == 2)
        {
            score += score / 10;
        }
        showLives();
        showScore();
        tvSolved.setText("You solved " + solved + " exercises.");

        if (checkNewHighscore()) {
            startNewHighscoreActivity();
            if (lives != 0 && sfxOn) {
                mpApplause.start();
            }
        }
        else {
            tvNewHighscore.setVisibility(View.INVISIBLE);
        }


    }

    private void startGameScreenActivity() {
        // addToScoreboard();
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
        finish();
    }

    private void startScoreBoardActivity() {
        // addToScoreboard();
        Intent intent = new Intent(this, ScoreBoard.class);
        startActivity(intent);
        finish();
    }

    private void startNewHighscoreActivity() {
        NewHighscore newHighscore = new NewHighscore(VictoryScreen.this);
        newHighscore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newHighscore.show();
    }

    private void showScore() {
        String newScore;
        if (score >= 10000) {
            newScore = Integer.toString(score);
        } else if (score >= 1000) {
            newScore = "0" + Integer.toString(score);
        } else if (score >= 100) {
            newScore = "00" + Integer.toString(score);
        } else if (score >= 10) {
            newScore = "000" + Integer.toString(score);
        } else if (score >= 1) {
            newScore = "0000" + Integer.toString(score);
        } else {
            newScore = "00000";
        }
        tvScore.setText(newScore);
    }

    private void showLives() {
        if (lives < 3){
            ivLife3.setVisibility(View.INVISIBLE);
        }
        if (lives < 2){
            ivLife2.setVisibility(View.INVISIBLE);
        }
        if (lives < 1){
            ivLife1.setVisibility(View.INVISIBLE);
        }
    }

    private boolean checkNewHighscore() {
        try {
            SQLiteDatabase scoreDB = this.openOrCreateDatabase("scoreboard",MODE_PRIVATE,null);
            Cursor cursor = scoreDB.rawQuery("SELECT  * FROM scores ORDER BY score DESC", null);
            cursor.moveToPosition(24);
            if (Integer.parseInt(cursor.getString(1)) >= score){
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}