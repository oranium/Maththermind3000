package com.example.aron.maththermind;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    // the base activity is necessary to check if the app is in foreground or background, we don't want to play background music while you are checking your mails or something like this

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static boolean isAppInFg = false;
    public static boolean isScrInFg = false;
    public static boolean isChangeScrFg = false;

    @Override
    protected void onStart() {
        if (!isAppInFg) {
            isAppInFg = true;
            isChangeScrFg = false;
            onAppStart();
        } else {
            isChangeScrFg = true;
        }
        isScrInFg = true;

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isScrInFg || !isChangeScrFg) {
            isAppInFg = false;
            onAppPause();
        }
        isScrInFg = false;
    }


    public void onAppStart() {
        if (MainScreen.musicOn) MainScreen.musicThread.resumePlayer();
    }

    public void onAppPause() {
        if (MainScreen.musicOn) MainScreen.musicThread.pausePlayer();
    }
}
