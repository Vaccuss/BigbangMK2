package com.example.dean.bigbangmk2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class SingleplayerActivity extends ActionBarActivity  implements SensorEventListener{

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    public boolean switchCase = false;
    public int counter;

    public SoundPool pool;
    public int gestureCompleate;
    public int lossSound;
    public int winSound;
    public int tieSound;
    boolean isloaded = false;

    public DatabaseOpenHelper databaseOpenHelper;
    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isloaded = true;
            }
        });

        gestureCompleate = pool.load(this, R.raw.gesturecompleate, 1);
        lossSound = pool.load(this, R.raw.youloss, 1);
        tieSound = pool.load(this, R.raw.youtie, 1);
        winSound = pool.load(this, R.raw.youwin, 1);

        databaseOpenHelper = new DatabaseOpenHelper(this);
        database = databaseOpenHelper.getWritableDatabase();

        DatabaseOpenHelper.pastPlayerScore(database, GameHub.playerName);

        database.close();

    }
    @Override
    public void onPause() {
        super.onPause();
        pool.release();
        pool = null;
        senSensorManager.unregisterListener(this);
        databaseOpenHelper = new DatabaseOpenHelper(this);
        database = databaseOpenHelper.getWritableDatabase();
        DatabaseOpenHelper.upDatePlayer(database);
        database.close();
        GameHub.loss = 0;
        GameHub.win = 0;
        GameHub.tie = 0;
        GameHub.playerName = "";
    }

    @Override
    public void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isloaded = true;
            }
        });

        gestureCompleate = pool.load(this, R.raw.gesturecompleate, 1);
        lossSound = pool.load(this, R.raw.youloss, 1);
        tieSound = pool.load(this, R.raw.youtie, 1);
        winSound = pool.load(this, R.raw.youwin, 1);

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_singleplayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;
        if (GameHub.getUserChoice() == GameHub.NO_SELECTION){
            Log.d("Not used", "");
        }else {
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[1];
                if (x >= 8.0) {
                    switchCase = true;
                    // Log.d("THE X VALUE:", Float.toString(x));

                }else if (x < 1.5 & switchCase){
                    counter += 1;
                    pool.play(gestureCompleate,  1, 1, 1, 0, 1);
                    Toast.makeText(this, "SHAKE: " + Integer.toString(counter), Toast.LENGTH_SHORT).show();
                    if (counter == 3){
                        Log.i("GAME PLAYED", Integer.toString(counter));
                        gameChoice();
                       counter = 0;
                    }
                    switchCase = false;
                }

            }
        }

    }

    public void setPlayerChoice(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.Rock:
                if (checked)
                    GameHub.setUserChoice(GameHub.ROCK);
                break;
            case R.id.Paper:
                if (checked)
                    GameHub.setUserChoice(GameHub.PAPER);
                break;
            case R.id.Scissors:
                if (checked)
                    GameHub.setUserChoice(GameHub.SICSSOR);
                break;
            case R.id.Lizard:
                if (checked)
                    GameHub.setUserChoice(GameHub.SICSSOR);
                break;
            case R.id.Spock:
                if (checked)
                    GameHub.setUserChoice(GameHub.SICSSOR);
                break;
        }

    }

    public void gameChoice() {
        GameHub.compare(GameHub.AiGuess());
        switch (GameHub.RESULT) {
            case GameHub.WIN:
                pool.play(winSound,  1, 1, 1, 0, 1);
                Toast.makeText(this, "YOU ARE WINNER!!!!!!", Toast.LENGTH_SHORT).show();
                GameHub.win = GameHub.win + 1;
                break;
            case GameHub.TIE:
                pool.play(tieSound,  1, 1, 1, 0, 1);
                GameHub.tie = GameHub.tie + 1;
                Toast.makeText(this, "YOU TIE WITH COMPUTER", Toast.LENGTH_SHORT).show();
                break;
            case GameHub.LOSS:
                pool.play(lossSound,  1, 1, 1, 0, 1);
                GameHub.loss = GameHub.loss + 1;
                Toast.makeText(this, "YOU LOSE SORRY", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void playGame(View view) {
        if (GameHub.getUserChoice() == GameHub.NO_SELECTION) {
            Toast.makeText(this, "Please Guess", Toast.LENGTH_LONG).show();
        } else {
            gameChoice();

        }

    }
}
