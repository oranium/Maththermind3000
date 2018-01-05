package com.example.aron.maththermind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aron.maththermind.R;

/**
 * Created by Aron on 25/12/2017.
 */

public class Sounds extends AppCompatActivity {
        private boolean sfx_on,music_on;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.popup_sounds);
            initialize_sounds();
        }

        private void initialize_sounds(){

        }

        private void toggle_music(){
            if(music_on == true){
                music_on = false;
            }
            else{
                music_on = true;
            }
        }

        private void toggle_sfx(){
            if(sfx_on == true){
                sfx_on = false;
            }
            else{
                sfx_on = true;
            }
        }
}
