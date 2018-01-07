package com.example.aron.maththermind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by breinhold on 20.12.17.
 */

public class GameScreen extends AppCompatActivity {
    TextView tvCurrentTime,tvCurrentScore,tvExercise;
    ImageView ivLife1,ivLife2,ivLife3;
    EditText etInput;
    int minute,second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        initialize_activity();
    }
    public void initialize_activity(){
        tvCurrentTime =(findViewById(R.id.txtView_currenttime));
        tvCurrentScore=(findViewById((R.id.txtView_currentscore)));
        tvExercise=(findViewById(R.id.txtView_exercise));
        ivLife1=findViewById(R.id.imageView_life1);
        ivLife2=findViewById(R.id.imageView_life2);
        ivLife3=findViewById(R.id.imageView_life3);
        etInput=findViewById(R.id.editTextInput);
        minute=1;
        second=60;
        timer_start();
    }

    public void timer_start(){
        Timer gameTimer = new Timer(true);
        gameTimer.schedule(new endGameTask(),1000*60);
        gameTimer.schedule(new currentTimeTask(),1000,1000);
    }

    private class currentTimeTask extends TimerTask{

        public void run(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(second==60){
                        tvCurrentTime.setText("0"+minute+":00");
                        minute--;
                    }
                    else if(second>9) {
                        tvCurrentTime.setText("0"+minute + ":" + second);
                    }
                    else{
                        tvCurrentTime.setText("0"+minute+":0"+second);
                    }

                    if(second>0){
                        second--;
                    }
                }
            });


        }
    }
    private class endGameTask extends TimerTask{
        public void run(){
            Intent intent= new Intent(GameScreen.this, VictoryScreen.class);
            startActivity(intent);
        }

    }
    }



