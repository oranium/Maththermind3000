package com.example.aron.maththermind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aron.maththermind.adapter.CustomListAdapter;
import com.example.aron.maththermind.model.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by breinhold on 20.12.17.
 */

public class VictoryScreen extends AppCompatActivity {

    SharedPreferences spScore;
    int score;
    int lives;

    List<Items> itemsList;
    Button btnPlayAgain;
    Button btnBackToMainMenu;
    Button btnToScoreBoard;
    TextView tvScore;
    TextView tvNewHighscore;
    ImageView ivLife1;
    ImageView ivLife2;
    ImageView ivLife3;
    MusicManager musicManager;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory_screen);
        this.musicManager = MainScreen.musicManager;
        spScore = getSharedPreferences("currentScore", MODE_PRIVATE);
        score = spScore.getInt("score", 0);
        lives = spScore.getInt("lives", 0);

        btnShare = findViewById(R.id.btn_share);
        btnPlayAgain = findViewById(R.id.btn_playagain);
        btnBackToMainMenu = findViewById(R.id.btn_vicScreen_to_menu);
        btnToScoreBoard = findViewById(R.id.btn_vicScreen_to_scoreboard);
        tvScore = findViewById(R.id.txtView_currentscore2);
        tvNewHighscore = findViewById(R.id.txtView_newHighscore);
        ivLife1 = findViewById(R.id.imageView_life);
        ivLife2 = findViewById(R.id.imageView_life5);
        ivLife3 = findViewById(R.id.imageView_life1);

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

        showLifes();
        showScore();

        if (checkNewHighscore())
        {
            startNewHighscoreActivity();
        }
        else
        {
            tvNewHighscore.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    protected void onPause(){
        super.onPause();
        if(musicManager!=null) {
            musicManager.pause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(musicManager.mp!=null&&!musicManager.mp.isPlaying()&&Sounds.musicOn) {
            musicManager.start();
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

    private void showLifes()
    {
        if (lives < 3) ivLife3.setVisibility(View.INVISIBLE);
        if (lives < 2) ivLife2.setVisibility(View.INVISIBLE);
        if (lives < 1) ivLife1.setVisibility(View.INVISIBLE);
    }

    private boolean checkNewHighscore()
    {
        try
        {
            SQLiteDatabase scoreDB = this.openOrCreateDatabase("scoreboard",MODE_PRIVATE,null);
            Cursor cursor = scoreDB.rawQuery("SELECT  * FROM scores ORDER BY score DESC", null);
            cursor.moveToPosition(9);
            if (Integer.parseInt(cursor.getString(1)) >= score) return false;
        }
        catch (Exception e)
        {}

        return true;
    }

    /*
    private void addToScoreboard() {
        SQLiteDatabase scoreDB;

        try {
            //create or access score database
            scoreDB = this.openOrCreateDatabase("scoreboard", MODE_PRIVATE, null);

            //table: name - score
            scoreDB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score INTEGER);");

            //select all rows from table
            Cursor cursor = scoreDB.rawQuery("SELECT * FROM scores", null);

            //if no rows: insert into table
            if (cursor != null) {
                    String name = getSharedPreferences("currentScore",MODE_PRIVATE).getString("EnteredName","");
                    scoreDB.execSQL("INSERT INTO scores (name,score) VALUES ('"+name+"','"+score+"');");
            }
        } catch (Exception e) {
        }

    }
    */
}