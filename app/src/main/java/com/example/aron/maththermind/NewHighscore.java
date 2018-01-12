package com.example.aron.maththermind;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class NewHighscore extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button save;
    public EditText etEnterName;
    SharedPreferences spScore;
    SharedPreferences.Editor spScoreEditor;

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
        spScore = getContext().getSharedPreferences("currentScore", Context.MODE_PRIVATE);
        spScoreEditor = spScore.edit();
        etEnterName.setText(spScore.getString("EnteredName", ""));
    }

    @Override
    public void onClick(View v)
    {
        // TODO: save highscore
        spScoreEditor.putString("EnteredName", etEnterName.getText().toString());
        spScoreEditor.commit();
        dismiss();
    }

}
