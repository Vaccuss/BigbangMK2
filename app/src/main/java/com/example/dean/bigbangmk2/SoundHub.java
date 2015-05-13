package com.example.dean.bigbangmk2;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.Builder;
import android.os.Build;
import android.util.Log;

/**
 * Created by Dean on 13/05/2015.
 */

public class SoundHub implements SoundPool.OnLoadCompleteListener {

    private static SoundHub instance = null;

    Context context;
    SoundPool pool;
    boolean ready;
    int gestureCompleate;
    int lossSound;
    int winSound;
    int tieSound;

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if (status != 0) {
            Log.e("SoundPoolDemo", "error loading resource");
            return;
        }
        Log.i("SoundPoolDemo", "loaded" + sampleId);
    }

    protected SoundHub() {
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        pool.setOnLoadCompleteListener(this);
        gestureCompleate = pool.load(context, R.raw.gestureCompleate, 1);
        lossSound = pool.load(context, R.raw.youloss, 1);
        tieSound = pool.load(context, R.raw.youtie, 1);
        winSound = pool.load(context, R.raw.youwin, 1);



    }

    public void playGesture(){
        pool.play(gestureCompleate, 1 , 1 , 1 , 0, 1);
    }

    public void playLoss(){
        pool.play(lossSound, 1 , 1 , 1 , 0, 1);
    }

    public void playWin(){
        pool.play(winSound, 1 , 1 , 1 , 0, 1);
    }

    public void playTie(){
        pool.play(tieSound, 1 , 1 , 1 , 0, 1);
    }


    public static SoundHub getInstance() {
        if (instance == null) {
            instance = new SoundHub();
        }
        return instance;
    }

}

