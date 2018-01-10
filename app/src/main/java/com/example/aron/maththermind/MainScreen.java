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

    private SharedPreferences spLvl;

    Button btn_start;
    Button btn_scoreboard;
    Button btn_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        spLvl = getSharedPreferences("level", MODE_PRIVATE);

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

        SharedPreferences spSound = getSharedPreferences("sound",MODE_PRIVATE);
        SharedPreferences spLevel = getSharedPreferences("level", MODE_PRIVATE);
        if(spSound.getInt("sfx",-1)==-1){
            SharedPreferences.Editor soundEditor = spSound.edit();
            soundEditor.putInt("sfx", 1);
            soundEditor.putInt("music",1);
            soundEditor.apply();
            //DEBUGGING ensues
            Toast.makeText(
                    this, "Edited preferences: sfx, music on!", Toast.LENGTH_LONG)
                    .show();
        }

        if(spLevel.getInt("lvlAdd",-1)==-1){
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
        if (spLvl.getInt("lvlAdd", 0) == 0 && spLvl.getInt("lvlSubt", 0) == 0 && spLvl.getInt("lvlMult", 0) == 0 && spLvl.getInt("lvlDiv", 0) == 0)
        {
            Toast.makeText(this, "Enable at least one operation in options.", Toast.LENGTH_LONG).show();
        }
        else
        {
            startActivity(new Intent(this, GameScreen.class));
        }
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
