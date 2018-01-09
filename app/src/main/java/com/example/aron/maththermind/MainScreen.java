package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        SharedPreferences spSound = this.getSharedPreferences("sound",MODE_PRIVATE);
        SharedPreferences spLevel = this.getSharedPreferences("level", MODE_PRIVATE);
        if(spSound.getInt("sfx",-3)==-3){
            SharedPreferences.Editor soundEditor = spSound.edit();
            soundEditor.putInt("sfx", 1);
            soundEditor.putInt("music",1);
            soundEditor.apply();
            //DEBUGGING ensues
            Toast.makeText(
                    this, "Edited preferences: sfx, music on!", Toast.LENGTH_LONG)
                    .show();
        }

        if(spLevel.getInt("lvlAdd",-3)==-3){
            SharedPreferences.Editor lvlEditor = spLevel.edit();
            lvlEditor.putInt("lvlAdd",1);
            lvlEditor.putInt("lvlSubt",1);
            lvlEditor.putInt("lvlDiv",1);
            lvlEditor.putInt("lvlMult",1);
            lvlEditor.apply();
            Toast.makeText(
                    this,"Edited preferences: enabled everything",Toast.LENGTH_LONG)
                    .show();

        }
    }

    private void startGameScreenActivity()
    {

        startActivity(new Intent(this, GameScreen.class));
    }

    private void startScoreBoardActivity()
    {
        startActivity(
                new Intent(this, ScoreBoard.class));
    }

    private void startOptionsActivity()
    {
        startActivity(
                new Intent(this, Options.class));
    }


}
