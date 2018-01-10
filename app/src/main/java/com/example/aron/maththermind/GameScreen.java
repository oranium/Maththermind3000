package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
/**
 * Created by breinhold on 20.12.17.
 */

public class GameScreen extends AppCompatActivity {
    TextView tvCurrentTime,tvCurrentScore,tvExercise;
    ImageView ivLife1,ivLife2,ivLife3;
    EditText etInput;
    int minute,second;
    int op1,op2,res;
    int score;
    short curOp;
    short lives;
    int addPoints,subtPoints,multPoints,divPoints;
    int addDif,subtDif,multDif,divDif;
    SharedPreferences spLevel;
    Intent intent;
    ConstraintLayout constraintLayout;
    Timer gameTimer;

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
        lives=3;
        score = 0;
        spLevel = getSharedPreferences("level",MODE_PRIVATE);
        constraintLayout = new ConstraintLayout(this);
        addDif=spLevel.getInt("lvlAdd",1);
        subtDif=spLevel.getInt("lvlSubt",1);
        multDif=spLevel.getInt("lvlMult",1);
        divDif=spLevel.getInt("lvlDiv",1);
        addPoints = 10*addDif;
        subtPoints = 20*subtDif;
        multPoints = 40*multDif;
        divPoints = 60*divDif;

        timer_start();

    }

    public void timer_start(){
        gameTimer = new Timer(true);
        gameTimer.schedule(new endGameTask(),1000*60);
        gameTimer.schedule(new currentTimeTask(),1000,1000);
    }

    private class currentTimeTask extends TimerTask{

        public void run(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(second==60){
                        minute--;
                        tvCurrentTime.setText("0"+minute+":59");

                    }
                    else if(second>9) {
                        tvCurrentTime.setText("0"+minute + ":" + (second-1));
                    }
                    else{
                        tvCurrentTime.setText("0"+minute+":0"+(second-1));
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
            gameTimer.cancel();
            intent = new Intent(GameScreen.this, VictoryScreen.class);
            startActivity(intent);
        }

    }

    //checks the result and adds points or subtracts lives
    public void check_res(int userRes,short curOp){
        //add corresponding number of points to score if task is right
        if(userRes==res){
            switch(curOp){
                case 0: score+=addPoints; break;
                case 1: score+=subtPoints; break;
                case 2: score+=multPoints; break;
                case 3: score+=divPoints; break;

            }
        //TODO: play right sound

        //subtract a life otherwise and end the game if lives 0
        }else{
            //TODO: play wrong sound
            lives--;
            switch(lives){
                case 2: constraintLayout.removeView(ivLife3);
                case 1: constraintLayout.removeView(ivLife2);
                case 0: constraintLayout.removeView(ivLife1);
            }

        }
        if(lives==0){
            gameTimer.cancel();
            //TODO: play sad sound
            startActivity(intent);
        }
    }
}



