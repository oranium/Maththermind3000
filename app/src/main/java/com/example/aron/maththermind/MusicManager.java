package com.example.aron.maththermind;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by aron2 on 16.01.18.
 */

public class MusicManager {
    static int current;
    static MediaPlayer mp;
    static Context context;

    public MusicManager(Context context){

        mp = MediaPlayer.create(context,R.raw.elevator_music);
        this.context = context;
    }

    public static void start() {

        if(mp!=null &&!mp.isPlaying() && mp.getCurrentPosition()>0){
            resume();
        }
        else{
            start(context,R.raw.elevator_music);
        }

    }

    public static void start(Context context, int music) {
        if (!mp.isPlaying()) {
            mp.start();
            mp.setLooping(true);
        }
    }

    public static void release(){
        if(mp!=null){
            if(mp.isPlaying()){
                mp.stop();
            }
            mp.release();
        }
    }

    public static void pause(){
        if(mp!=null && mp.isPlaying()){
            current = mp.getCurrentPosition();
            mp.pause();
        }
    }

    public static void resume(){
        mp.seekTo(current);
        mp.start();
        mp.setLooping(true);
    }
}
