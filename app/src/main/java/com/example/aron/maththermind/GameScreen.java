package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
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
    List<Integer> nextOp;
    int nextOpSize;
    SharedPreferences spLevel;
    Intent intent;
    ConstraintLayout constraintLayout;
    Timer gameTimer;
    Random rand;

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
        nextOp = new ArrayList<>();
        //note that 0:addition , 1:subtraction, 2:multiplication , 3: division
        if(!(addDif==0)){
            nextOp.add(0);
        }
        if(!(subtDif==0)){
            nextOp.add(1);
        }
        if(!(multDif==0)){
            nextOp.add(2);
        }
        if(!(divDif==0)){
            nextOp.add(3);

        }

        nextOpSize = nextOp.size();
        timer_start();
        /*TODO: make this work and hand it over to generate_exercise(int curOp)
         * <code> curOp = nextOp.get(rand.nextInt(nextOpSize)); </code>
         */
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

    public void generate_exercise(int curOp){
        switch(curOp){
            case 0: generate_addition(); break;
            case 1: generate_subtraction(); break;
            case 2: generate_multiplication(); break;
            case 3: generate_division(); break;
        }
    }

    public void generate_addition(){
        switch(addDif){
            case 1: op1=rand.nextInt(21);
                op2=rand.nextInt(21-op1);
                res=op1+op2;
                break;
            case 2: op1=rand.nextInt(101);
                op2=rand.nextInt(101-op1);
                res=op1+op2;
                break;
            case 3: op1=rand.nextInt(1001);
                op2=rand.nextInt(1001-op1);
                res=op1+op2;
                break;
        }
    }

    public void generate_subtraction(){
        switch(subtDif){
            case 1: op1=rand.nextInt(21);
                    op2=rand.nextInt(21-op1);
                    res=op1-op2;
                    break;
            case 2: op1=rand.nextInt(101);
                    op2=rand.nextInt(101-op1);
                    res=op1-op2;
                    break;
            case 3: op1=rand.nextInt(1001);
                    op2=rand.nextInt(1001-op1);
                    res=op1-op2;
                    break;
        }
    }

    public void generate_multiplication(){
        switch(multDif){
            case 1: op1=rand.nextInt(11);
                    op2=rand.nextInt(11);
                    res=op1*op2;
                    break;
            case 2: op1=rand.nextInt(21);
                    op2=rand.nextInt(21);
                    res=op1*op2; break;
            case 3: op1=rand.nextInt(31);
                    op2=rand.nextInt(31);
                    res=op1*op2; break;
        }
    }

    //this is done by switching variables from multiplication. maybe its wrong. am lazy.
    public void generate_division(){
        switch(divDif){
            case 1: res=rand.nextInt(11);
                    op2=rand.nextInt(11);
                    op1=res*op2;
                    break;
            case 2: res=rand.nextInt(21);
                    op2=rand.nextInt(21);
                    op1=res*op2;break;
            case 3: res=rand.nextInt(31);
                    op2=rand.nextInt(31);
                    op1=res*op2; break;
        }
    }
}



