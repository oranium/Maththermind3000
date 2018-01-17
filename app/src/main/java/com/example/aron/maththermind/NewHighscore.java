package com.example.aron.maththermind;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class NewHighscore extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button save;
    public EditText etEnterName;
    SharedPreferences spScore;
    SharedPreferences.Editor spScoreEditor;

    int score, solved;

    public NewHighscore(Activity a)
    {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_highscore);

        etEnterName = findViewById(R.id.etEnterName);
        save = findViewById(R.id.btn_saveHighscore);
        save.setOnClickListener(this);
        spScore = getContext().getSharedPreferences("currentScore", MODE_PRIVATE);
        spScoreEditor = spScore.edit();
        etEnterName.setText(spScore.getString("EnteredName", ""));
        score = spScore.getInt("score", 0);
        solved = spScore.getInt("solved", 0);
    }

    @Override
    public void onClick(View v)
    {
        String nameCheck = etEnterName.getText().toString().replace(" ", "");
        if (nameCheck.length() == 0 || etEnterName.getText().toString() == "")
        {
            Toast.makeText(getContext(), "Please enter your name.", Toast.LENGTH_LONG).show();
        }
        else
        {
            spScoreEditor.putString("EnteredName", etEnterName.getText().toString());
            spScoreEditor.commit();
            addToScoreboard();
            dismiss();
        }
    }

    private void addToScoreboard() {
        SQLiteDatabase scoreDB;

        try {
            //create or access score database
            scoreDB = getContext().openOrCreateDatabase("scoreboard", MODE_PRIVATE, null);

            //table: name - score
            scoreDB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score INTEGER, solved INTEGER);");

            //select all rows from table
            Cursor cursor = scoreDB.rawQuery("SELECT * FROM scores", null);

            //if no rows: insert into table
            if (cursor != null) {
                String name = spScore.getString("EnteredName","");
                scoreDB.execSQL("INSERT INTO scores (name,score,solved) VALUES ('"+name+"','"+score+"','"+solved+"');");
            }
        } catch (Exception e) {
        }

    }


}
