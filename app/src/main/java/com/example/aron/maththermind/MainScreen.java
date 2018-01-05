package com.example.aron.maththermind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button btn_start;
    Button btn_scoreboard;
    Button btn_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btn_start = findViewById(R.id.btn_start);
        btn_scoreboard = findViewById(R.id.btn_scoreboard);
        btn_options = findViewById(R.id.btn_options);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameScreenActivity();
            }
        });

        btn_scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScoreBoardActivity();
            }
        });

        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOptionsActivity();
            }
        });
    }

    private void startGameScreenActivity()
    {
        startActivity(new Intent(this, GameScreen.class));
    }

    private void startScoreBoardActivity()
    {
        startActivity(new Intent(this, ScoreBoard.class));
    }

    private void startOptionsActivity()
    {
        startActivity(new Intent(this, Options.class));
    }

}
