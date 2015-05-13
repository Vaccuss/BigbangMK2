package com.example.dean.bigbangmk2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;


public class SingleplayerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
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


    public void playGame(View view) {
        if (GameHub.getUserChoice() == GameHub.NO_SELECTION) {
            Toast.makeText(this, "Please Guess", Toast.LENGTH_LONG).show();
        } else {
            GameHub.compare(GameHub.AiGuess());
            Toast.makeText(this, "Computer is thinking", Toast.LENGTH_SHORT).show();

            switch (GameHub.RESULT) {
                case GameHub.WIN:
                    Toast.makeText(this, "YOU ARE WINNER!!!!!!", Toast.LENGTH_LONG).show();
                    break;
                case GameHub.TIE:
                    Toast.makeText(this, "YOU TIE WITH COMPUTER", Toast.LENGTH_LONG).show();
                default:
                    Toast.makeText(this, "COMPUTER BEAT YOU!!!!!", Toast.LENGTH_LONG).show();
            }

        }

    }
}
