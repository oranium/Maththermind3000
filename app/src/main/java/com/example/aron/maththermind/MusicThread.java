package com.example.aron.maththermind;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Aron on 17/01/2018.
 */

public class MusicThread extends Thread{
        Context context;
        MediaPlayer mp;
        int current;

        MusicThread(Context context){
            this.context = context;
        }

        @Override
        public void run(){
            mp = MediaPlayer.create(context, R.raw.elevator_music);
            if (MainScreen.musicOn){
                mp.start();
                mp.setLooping(true);
            }
        }

        public void stopPlayer(){
            mp.stop();
            mp.release();
            mp = null;
        }

        public void pausePlayer(){
            current = mp.getCurrentPosition();
            mp.pause();
        }

        public void resumePlayer(){
            if(mp!=null&&!mp.isPlaying()) {
                mp.seekTo(current);
                mp.start();
                mp.setLooping(true);
            }
        }

}
