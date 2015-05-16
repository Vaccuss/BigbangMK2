package com.example.dean.bigbangmk2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public  DatabaseOpenHelper databaseOpenHelper;
    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseOpenHelper = new DatabaseOpenHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  Indicates a change in the Wi-Fi P2P status.

        getMenuInflater().inflate(R.menu.menu_main, menu);
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







    public void toSinglePlayer(View view){
        EditText playerName = (EditText) findViewById(R.id.playerEditText);
        String name = playerName.getText().toString();

        database = databaseOpenHelper.getWritableDatabase();
       if (!databaseOpenHelper.playerCheck(database, name)){
           databaseOpenHelper.addNewPlayer(database, name);



           GameHub.playerName = name;
       }
        Cursor cursor = database.query(true, "HIGHSCORES", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String crstname = cursor.getString(0);
            String crstdescription = cursor.getString(1);
            String when = cursor.getString(2);
            String prtstring = String.format("%s %s %s", crstname, crstdescription, when);
            Log.i("Item: ", prtstring);
        }
        cursor.close();
        database.close();
        Intent intent = new Intent(this, SingleplayerActivity.class);
        startActivity(intent);
    }

    public void toMultiplayer(View view){
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivity(intent);

    }
    public void toScores(View view){
        Intent intent = new Intent(this, Highscore.class);
        startActivity(intent);

    }







}
