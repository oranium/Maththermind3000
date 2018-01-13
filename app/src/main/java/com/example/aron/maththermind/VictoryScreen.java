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

    ListView listView;
    List<Items> itemsList;
    CustomListAdapter adapter;
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

        // TODO: check new highscore
        startNewHighscoreActivity();
    }

    private void startGameScreenActivity() {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
        finish();
    }

    private void startScoreBoardActivity() {
        addToScoreboard();
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
        } else {
            newScore = "00000";
        }
        tvScore.setText(newScore);
    }

    private void addToScoreboard() {
        itemsList = new ArrayList<>();
        SQLiteDatabase scoreDB = null;

        try {
            //create or access score database
            scoreDB = this.openOrCreateDatabase("scoreboard", MODE_PRIVATE, null);

            //table: name - score
            scoreDB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

            //select all rows from table
            Cursor cursor = scoreDB.rawQuery("SELECT * FROM scores", null);

            //if no rows: insert into table
            if (cursor != null) {
                if (cursor.getCount() < 10) {
                    String name = getSharedPreferences("currentScore",MODE_PRIVATE).getString("EnteredName","");
                    scoreDB.execSQL("INSERT INTO scores (name,score) VALUES ('"+name+"','"+score+"');");
                }
            }
        } catch (Exception e) {
        }

    }
}