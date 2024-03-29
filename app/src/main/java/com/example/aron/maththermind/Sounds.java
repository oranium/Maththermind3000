package com.example.aron.maththermind;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Aron on 25/12/2017.
 */

public class Sounds extends BaseActivity {
        ToggleButton tbSfx,tbMusic;
        Button btnBack;
        private SharedPreferences spSound;
        private SharedPreferences.Editor spEditor;
        public static boolean sfxOn;
        Boolean musicOn;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.popup_sounds);

            btnBack = findViewById(R.id.btn_back2);
            spSound = getSharedPreferences("sound", Context.MODE_PRIVATE);
            spEditor = spSound.edit();
            tbMusic =(ToggleButton) findViewById(R.id.toggleButton_music);
            tbSfx =(ToggleButton) findViewById(R.id.toggleButton_sfx);
            sfxOn = spSound.getBoolean("sfx",false);
            musicOn =(spSound.getBoolean("music",false));
            tbMusic.setChecked(musicOn);
            tbSfx.setChecked(sfxOn);
            tbSfx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_sfx();
                }
            });
            tbMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_music();
                }
            });
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }

        private void toggle_music(){
            //if music on, turn off
            if(spSound.getBoolean("music",false)){
                spEditor.putBoolean("music",false);
                Toast.makeText(this,"Music off!",Toast.LENGTH_SHORT).show();
                MainScreen.musicThread.stopPlayer();
                MainScreen.musicOn = false;
            }
            else{
                spEditor.putBoolean("music",true);
                Toast.makeText(this,"Music on!",Toast.LENGTH_SHORT).show();
                musicOn =(spSound.getBoolean("music",false));
                MainScreen.musicOn = true;
                MainScreen.musicThread.run();
            }
            spEditor.apply();
            musicOn =(spSound.getBoolean("music",false));
        }

        private void toggle_sfx(){
            //if sfx on, turn off
            if(spSound.getBoolean("sfx",false)){
                spEditor.putBoolean("sfx",false);
                Toast.makeText(this,"SFX off!",Toast.LENGTH_SHORT).show();
            }
            else{
                spEditor.putBoolean("sfx",true);
                Toast.makeText(this,"SFX on!",Toast.LENGTH_SHORT).show();
            }
            spEditor.apply();

        }

}
