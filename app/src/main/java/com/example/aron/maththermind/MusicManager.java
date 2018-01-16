package com.example.aron.maththermind;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by aron2 on 16.01.18.
 */

public class MusicManager {
    static int current;
    static MediaPlayer mp;

    public static void start(Context context) {

        if(mp!=null &&!mp.isPlaying() && mp.getCurrentPosition()>0){
            resume();
        }
        else if(mp==null){
            start(context, R.raw.elevator_music);
        }

    }

    public static void start(Context context, int music) {
        mp = MediaPlayer.create(context, music);
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
