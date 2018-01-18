package com.example.aron.maththermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
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
    int score, solved;
    short lives;
    int addPoints,subtPoints,multPoints,divPoints;
    int addDif,subtDif,multDif,divDif;
    Boolean sfxOn,musicOn;
    List<Integer> nextOp;
    int nextOpSize;
    int curOp;
    SharedPreferences spLevel,spScore,spSound;
    SharedPreferences.Editor spScoreEditor;
    Intent intent;
    Timer gameTimer;
    Random rand;
    MediaPlayer mpWrong,mpCorrect,mpDead;
    MusicThread musicThread;

    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        initialize_activity();
    }




    public void initialize_activity(){
        musicThread = MainScreen.musicThread;
        rand = new Random();
        mpWrong = MediaPlayer.create(this,R.raw.wrong_sfx);
        mpCorrect = MediaPlayer.create(this,R.raw.correct_sfx);
        mpDead = MediaPlayer.create(this,R.raw.sad_horn);
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
        solved = 0;
        spLevel = getSharedPreferences("level",MODE_PRIVATE);
        spScore = getSharedPreferences("currentScore", MODE_PRIVATE);
        spSound = getSharedPreferences("sound",MODE_PRIVATE);
        spScoreEditor = spScore.edit();
        addDif=spLevel.getInt("lvlAdd",1);
        subtDif=spLevel.getInt("lvlSubt",1);
        multDif=spLevel.getInt("lvlMult",1);
        divDif=spLevel.getInt("lvlDiv",1);
        addPoints = 10*addDif;
        subtPoints = 20*subtDif;
        multPoints = 40*multDif;
        divPoints = 60*divDif;
        sfxOn = spSound.getBoolean("sfx",false);
        musicOn = MainScreen.musicOn;
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

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence query, int i, int i1, int i2) {
                if (query.length() == Integer.toString(res).length())
                {
                    check_res(Integer.parseInt(query.toString()));
                    generate_exercise();
                    etInput.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        generate_exercise();

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
                    else if(second>10) {
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
            startVictoryScreenActivity();
        }

    }

    //checks the result and adds points or subtracts lives
    public void check_res(int userRes){
        //add corresponding number of points to score if task is right
        if (userRes == res)
        {
            switch (curOp)
            {
                case 0:
                    score += addPoints;
                    break;
                case 1:
                    score += subtPoints;
                    break;
                case 2:
                    score += multPoints;
                    break;
                case 3:
                    score += divPoints;
                    break;
            }
            solved++;
            updateScore();
            if(sfxOn) {
                if (mpCorrect.isPlaying()) {
                    mpCorrect.stop();
                    try {
                        mpCorrect.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (mpWrong.isPlaying()) {
                    mpWrong.stop();
                    try {
                        mpWrong.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mpCorrect.start();
            }

        //subtract a life otherwise and end the game if lives 0
        }
        else {
            lives--;
            switch (lives) {
                case 2:
                    ivLife3.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    ivLife2.setVisibility(View.INVISIBLE);
                    break;
                case 0:
                    ivLife1.setVisibility(View.INVISIBLE);
                    break;
            }

            if (sfxOn) {
                if (mpCorrect.isPlaying()) {
                    mpCorrect.stop();
                    try {
                        mpCorrect.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if (mpWrong.isPlaying()) {
                    mpWrong.stop();
                    try {
                        mpWrong.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (lives > 0) {
                    mpWrong.start();
                }
            }
        }

        if (lives == 0)
        {
            if(sfxOn) {

                mpWrong.stop();
                mpDead.start();
            }
            gameTimer.cancel();
            startVictoryScreenActivity();
        }
    }

    private void updateScore()
    {
        String newScore = "";
        if (score >= 10000)
        {
            newScore = Integer.toString(score);
        }
        else if (score >= 1000)
        {
            newScore = "0" + Integer.toString(score);
        }
        else if (score >= 100)
        {
            newScore = "00" + Integer.toString(score);
        }
        else if (score >= 10)
        {
            newScore = "000" + Integer.toString(score);
        }
        tvCurrentScore.setText(newScore);
    }

    private void getNextOp()
    {
        curOp = rand.nextInt(nextOpSize);
        curOp = nextOp.get(curOp);
    }

    public void generate_exercise(){
        getNextOp();
        String newExercise = "";
        switch(curOp){
            case 0:
                generate_addition();
                newExercise = op1 + " + ";
                break;
            case 1:
                generate_subtraction();
                newExercise = op1 + " - ";
                break;
            case 2:
                generate_multiplication();
                newExercise = op1 + " * ";
                break;
            case 3:
                generate_division();
                newExercise = Integer.toString(op1) + " : ";
                break;
        }
        newExercise += Integer.toString(op2) + " = ?";
        tvExercise.setText(newExercise);
    }

    public void generate_addition(){
        switch(addDif){
            case 1:
                res = rand.nextInt(21);
                op1 = rand.nextInt(res + 1);
                op2 = res - op1;
                break;
            case 2:
                res = rand.nextInt(81) + 20;
                op1 = rand.nextInt(res + 1);
                op2 = res - op1;
                break;
            case 3:
                res = rand.nextInt(901) + 100;
                op1 = rand.nextInt(res + 1);
                op2 = res - op1;
                break;
        }
    }

    public void generate_subtraction(){
        switch(subtDif){
            case 1:
                op1 = rand.nextInt(21);
                op2 = rand.nextInt(op1 + 1);
                res = op1 - op2;
                break;
            case 2:
                op1 = rand.nextInt(81) + 20;
                op2 = rand.nextInt(op1 + 1);
                res = op1 - op2;
                break;
            case 3:
                op1 = rand.nextInt(901) + 100;
                op2 = rand.nextInt(op1 + 1);
                res = op1 - op2;
                break;
        }
    }

    public void generate_multiplication(){
        switch(multDif){
            case 1:
                op1 = rand.nextInt(11);
                op2 = rand.nextInt(11);
                res = op1 * op2;
                break;
            case 2:
                op1 = rand.nextInt(21);
                op2 = rand.nextInt(21);
                if (op1 < 10 && op2 < 10)
                {
                    switch (rand.nextInt(2))
                    {
                        case 0:
                            op1 = rand.nextInt(11) + 10;
                            break;
                        case 1:
                            op2 = rand.nextInt(11) + 10;
                            break;
                    }
                }
                res = op1 * op2;
                break;
            case 3:
                op1 = rand.nextInt(31);
                op2 = rand.nextInt(31);
                if (op1 < 20 && op2 < 20)
                {
                    switch (rand.nextInt(2))
                    {
                        case 0:
                            op1 = rand.nextInt(11) + 20;
                            break;
                        case 1:
                            op2 = rand.nextInt(11) + 20;
                            break;
                    }
                }
                res = op1 * op2;
                break;
        }
    }

    //this is done by switching variables from multiplication. maybe its wrong. am lazy. <-- yes, it was wrong. :D
    public void generate_division(){
        switch(divDif){
            case 1:
                res = rand.nextInt(11);
                op2 = rand.nextInt(10) + 1;
                op1 = res * op2;
                break;
            case 2:
                res = rand.nextInt(21);
                op2 = rand.nextInt(10) + 11;
                op1 = res * op2;
                break;
            case 3:
                res = rand.nextInt(31);
                op2 = rand.nextInt(10) + 21;
                op1 = res * op2;
                break;
        }
    }

    private void startVictoryScreenActivity()
    {
        spScoreEditor.putInt("score", score);
        spScoreEditor.putInt("lives", lives);
        spScoreEditor.putInt("solved", solved);
        spScoreEditor.commit();
        intent = new Intent(GameScreen.this, VictoryScreen.class);
        startActivity(intent);
        mpCorrect.release();
        mpWrong.release();
        mpCorrect = null;
        mpWrong = null;
        finish(); //I HOPE THIS CLOSES THE ACTIVITY <-- yes, it does.
    }

    @Override
    public void onBackPressed()
    {
        gameTimer.cancel();
        finish();
    }

}
