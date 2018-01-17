package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

public class MainScreen extends AppCompatActivity {

    private SharedPreferences spLvl,spSound;
    Button btn_start;
    Button btn_scoreboard;
    Button btn_options;
    static MusicManager musicManager;
    static boolean musicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initializeApp();
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
        if(musicManager.mp!=null&&!musicManager.mp.isPlaying()&&spSound.getBoolean("music",false)) {
                musicManager.start();
            }
    }

    private void initializeApp(){
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
        spLvl = getSharedPreferences("level", MODE_PRIVATE);
        spSound = getSharedPreferences("sound",MODE_PRIVATE);

        if(spSound.getBoolean("exists",false)==false){
            SharedPreferences.Editor soundEditor = spSound.edit();
            soundEditor.putBoolean("sfx", false);
            soundEditor.putBoolean("music",false);
            soundEditor.putBoolean("exists",true);
            musicOn = false;
            soundEditor.apply();

        }
            if(musicManager.mp==null){
                musicManager = new MusicManager(this);
                musicManager.start();
                musicManager.pause();
                musicOn = false;
            }
            if(spSound.getBoolean("music",false)&&!musicManager.mp.isPlaying()){
                musicManager.start();
                musicOn = true;
            }



        if(spLvl.getInt("lvlAdd",-1)==-1){
            SharedPreferences.Editor lvlEditor = spLvl.edit();
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
            Intent intent = new Intent(this, GameScreen.class);
            startActivity(intent);
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
