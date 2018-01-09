package com.example.aron.maththermind;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aron on 25/12/2017.
 */

public class Gamemode extends AppCompatActivity {
    private SharedPreferences spLvl;
    private SharedPreferences.Editor lvlEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_gamemode);
        initialize_activity();
    }
    private void initialize_activity(){
        spLvl = getSharedPreferences("level",MODE_PRIVATE);
        lvlEditor= spLvl.edit();
    }
    //the following 4 functions take the current level of each of their respective seekbars and
    //change the difficulty level in their sp's
    private void changeAdd(short seekbarLevel){
        switch(seekbarLevel){
            case 0: lvlEditor.putInt("lvlAdd",0);
                    break;
            case 1: lvlEditor.putInt("lvlAdd",1);
                break;
            case 2: lvlEditor.putInt("lvlAdd",2);
                break;
            case 3: lvlEditor.putInt("lvlAdd",3);
                break;
        }
        lvlEditor.apply();
    }

    private void changeSubt(short seekbarLevel){
        switch(seekbarLevel){
            case 0: lvlEditor.putInt("lvlSubt",0);
                break;
            case 1: lvlEditor.putInt("lvlSubt",1);
                break;
            case 2: lvlEditor.putInt("lvlSubt",2);
                break;
            case 3: lvlEditor.putInt("lvlSubt",3);
                break;
        }
        lvlEditor.apply();
    }

    private void changeMult(short seekbarLevel){
        switch(seekbarLevel){
            case 0: lvlEditor.putInt("lvlMult",0);
                break;
            case 1: lvlEditor.putInt("lvlMult",1);
                break;
            case 2: lvlEditor.putInt("lvlMult",2);
                break;
            case 3: lvlEditor.putInt("lvlMult",3);
                break;
        }
        lvlEditor.apply();
    }

    private void changeDiv(short seekbarLevel){
        switch(seekbarLevel){
            case 0: lvlEditor.putInt("lvlDiv",0);
                break;
            case 1: lvlEditor.putInt("lvlDiv",1);
                break;
            case 2: lvlEditor.putInt("lvlDiv",2);
                break;
            case 3: lvlEditor.putInt("lvlDiv",3);
                break;
        }
        lvlEditor.apply();
    }
}



