package com.example.aron.maththermind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by breinhold on 20.12.17.
 */

public class Options extends BaseActivity {
    Button btn_gameMode;
    Button btn_sounds;
    Button btn_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        btn_gameMode = findViewById(R.id.btn_gameMode);
        btn_sounds = findViewById(R.id.btn_sounds);
        btn_back = findViewById(R.id.btn_back5);

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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void startGamemodeActivity() {
        startActivity(new Intent(this, Gamemode.class));
    }

    private void startSoundsActivity() {
        startActivity(new Intent(this, Sounds.class));
    }

}
