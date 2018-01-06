package com.example.aron.maththermind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by breinhold on 20.12.17.
 */

public class Options extends AppCompatActivity {
    Button btn_gameMode;
    Button btn_sounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        initialize_options();
    }

    private void initialize_options(){
        btn_gameMode = findViewById(R.id.btn_gameMode);
        btn_sounds = findViewById(R.id.btn_sounds);

        btn_gameMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGamemodeActivity();


            }
        });

        btn_sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSoundsActivity();
            }
        });
    }

    private void startGamemodeActivity()
    {
        startActivity(new Intent(this, Gamemode.class));
    }

    private void startSoundsActivity()
    {
        startActivity(new Intent(this, Sounds.class));
    }

}
