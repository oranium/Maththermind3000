package com.example.aron.maththermind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.Timer;
/**
 * Created by breinhold on 20.12.17.
 */

public class GameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        initialize_activity();
    }
    public void initialize_activity(){
        timer_start();
    }

    public void timer_start(){
        Timer timer = new Timer();
    }
}

