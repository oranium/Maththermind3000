package com.example.aron.maththermind;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Aron on 25/12/2017.
 */

public class Gamemode extends BaseActivity {

    private SharedPreferences spLvl;
    private SharedPreferences.Editor lvlEditor;
    SeekBar seekBarAddition;
    SeekBar seekBarSubtraction;
    SeekBar seekBarMultiplication;
    SeekBar seekBarDivision;
    SeekBar seekBarHardcore;
    Button btnBack;
    int scoreForHardcore = 2500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_gamemode);
        spLvl = getSharedPreferences("level", MODE_PRIVATE);
        lvlEditor = spLvl.edit();
        seekBarAddition = findViewById(R.id.seekBar_addition);
        seekBarSubtraction = findViewById(R.id.seekBar_subtraction);
        seekBarMultiplication = findViewById(R.id.seekBar_multiplication);
        seekBarDivision = findViewById(R.id.seekBar_division);
        seekBarHardcore = findViewById(R.id.seekBar_hardcore);
        seekBarAddition.setProgress(spLvl.getInt("lvlAdd", 1));
        seekBarSubtraction.setProgress(spLvl.getInt("lvlSubt", 1));
        seekBarMultiplication.setProgress(spLvl.getInt("lvlMult", 1));
        seekBarDivision.setProgress(spLvl.getInt("lvlDiv", 1));
        seekBarHardcore.setProgress(spLvl.getInt("hardcore", 0));
        btnBack = findViewById(R.id.btn_back1);


        if (!isHardcoreEnabled())
        {
            seekBarHardcore.setEnabled(false);
            Toast.makeText(this, "Reach " + scoreForHardcore + " points to enable the hardcore mode!", Toast.LENGTH_LONG).show();
        }
        if (spLvl.getInt("hardcore", 0) == 1)
        {
            seekBarAddition.setEnabled(false);
            seekBarSubtraction.setEnabled(false);
            seekBarMultiplication.setEnabled(false);
            seekBarDivision.setEnabled(false);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seekBarAddition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                changeAdd((short) i);
                checkLevel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarSubtraction.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                changeSubt((short) i);
                checkLevel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarMultiplication.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                changeMult((short) i);
                checkLevel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarDivision.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                changeDiv((short) i);
                checkLevel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarHardcore.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                changeHard((short) i);
                checkLevel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    //the following 4 functions take the current level of each of their respective seekbars and
    //change the difficulty level in their sp's
    private void changeAdd(short seekBarLevel){
        switch(seekBarLevel){
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

    private void changeSubt(short seekBarLevel){
        switch(seekBarLevel){
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

    private void changeMult(short seekBarLevel){
        switch(seekBarLevel){
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

    private void changeDiv(short seekBarLevel){
        switch(seekBarLevel){
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

    private void changeHard(short seekBarLevel)
    {
        switch(seekBarLevel) {
            case 0:
                lvlEditor.putInt("hardcore", 0);
                seekBarAddition.setEnabled(true);
                seekBarSubtraction.setEnabled(true);
                seekBarMultiplication.setEnabled(true);
                seekBarDivision.setEnabled(true);
                break;
            case 1:
                lvlEditor.putInt("hardcore", 1);
                seekBarAddition.setEnabled(false);
                seekBarSubtraction.setEnabled(false);
                seekBarMultiplication.setEnabled(false);
                seekBarDivision.setEnabled(false);
                break;
        }
        lvlEditor.apply();
    }

    private void checkLevel() {
        if (spLvl.getInt("lvlAdd", 0) == 0 && spLvl.getInt("lvlSubt", 0) == 0 && spLvl.getInt("lvlMult", 0) == 0 && spLvl.getInt("lvlDiv", 0) == 0 && spLvl.getInt("hardcore", 0) == 0) {
            Toast.makeText(this, "Enable at least one operation.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isHardcoreEnabled()
    {
        try {
            SQLiteDatabase scoreDB = this.openOrCreateDatabase("scoreboard",MODE_PRIVATE,null);
            Cursor cursor = scoreDB.rawQuery("SELECT  * FROM scores ORDER BY score DESC", null);
            cursor.moveToPosition(0);
            if (Integer.parseInt(cursor.getString(1)) >= scoreForHardcore){
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}



