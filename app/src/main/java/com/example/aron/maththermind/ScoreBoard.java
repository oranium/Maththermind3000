package com.example.aron.maththermind;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by breinhold on 20.12.17.
 */

public class ScoreBoard extends AppCompatActivity {
    LinearLayout llscoreBoard;
    SharedPreferences spScore;
    SharedPreferences.Editor spScoreEditor;
    RelativeLayout.LayoutParams params1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize_ScoreBoard();
        setContentView(R.layout.score_board);

    }

    private void initialize_ScoreBoard(){
        llscoreBoard = findViewById(R.id.score_board);
        spScore = getSharedPreferences("score",MODE_PRIVATE);
        spScoreEditor = spScore.edit();

        for(int i=1;i<=spScore.getAll().size();i++){
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setText(spScore.getInt("score",1));
            tv.setTextColor(Color.BLACK);
            llscoreBoard.addView(tv);
        }
    }
}
