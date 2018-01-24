package com.example.aron.maththermind;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Credits extends BaseActivity
{

    Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        btnBack = findViewById(R.id.btn_back6);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
