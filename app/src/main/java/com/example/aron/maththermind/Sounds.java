package com.example.aron.maththermind;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.aron.maththermind.R;

/**
 * Created by Aron on 25/12/2017.
 */

public class Sounds extends AppCompatActivity {
        private boolean sfx_on,music_on;
        private SharedPreferences spSound;
        private SharedPreferences.Editor spEditor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.popup_sounds);
            initialize_sounds();
        }

        private void initialize_sounds(){
            spSound = getSharedPreferences("sfx", Context.MODE_PRIVATE);
            spEditor = spSound.edit();
        }
        //use this on music toggle pressed
        private void toggle_music(){
            //if music on, turn off
            if(spSound.getInt("music",-3)==1){
                spEditor.putInt("music",0);
                Toast.makeText(this,"Music off!",Toast.LENGTH_LONG).show();
            }
            else{
                spEditor.putInt("music",1);
                Toast.makeText(this,"Music on!",Toast.LENGTH_LONG).show();
            }
            spEditor.apply();
        }
        //use this on SFX button press
        private void toggle_sfx(){
            //if sfx on, turn off
            if(spSound.getInt("sfx",-3)==1){
                spEditor.putInt("sfx",0);
                Toast.makeText(this,"SFX off!",Toast.LENGTH_LONG).show();
            }
            else{
                spEditor.putInt("sfx",1);
                Toast.makeText(this,"SFX on!",Toast.LENGTH_LONG).show();
            }
            spEditor.apply();

        }

}
