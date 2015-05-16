package com.example.dean.bigbangmk2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Highscore extends ActionBarActivity {

    public ListView scoreList;
    public ArrayList<String[]> players = new ArrayList<>();
    public DatabaseOpenHelper databaseOpenHelper;
    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        scoreList = (ListView) findViewById(R.id.scoreList);
        databaseOpenHelper = new DatabaseOpenHelper(this);

        database = databaseOpenHelper.getWritableDatabase();

        Cursor cursor = databaseOpenHelper.rankDatabase(database);
        while (cursor.moveToNext()) {
            String playerName = cursor.getString(0);
            String wins = cursor.getString(1);
            String losses = cursor.getString(2);
            String tie = cursor.getString(3);
            String totalGames = cursor.getString(4);
            String winpercent = cursor.getString(5);
            Log.i("INFO", winpercent);

            String[] row = new String[]{playerName,wins,losses,tie, totalGames};
           players.add(row);
        }
        cursor.close();
        database.close();

        ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_item, players);
        scoreList.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
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

    class ScoreAdapter extends ArrayAdapter<String[]> {

        ArrayList<String[]> players;

        public ScoreAdapter(Context context, int resource, ArrayList<String[]> objects) {
            super(context, resource, objects);
            players = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View v = convertView;

            if (v == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.score_item, null);
            }

            String[] currentPlayer = players.get(position);

            if (currentPlayer != null) {

                Log.i("Adapter", position + "");

                TextView rank = (TextView) v.findViewById(R.id.rank);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView win = (TextView) v.findViewById(R.id.win);
                TextView loss = (TextView) v.findViewById(R.id.loss);
                TextView tie = (TextView) v.findViewById(R.id.tie);
                TextView total = (TextView) v.findViewById(R.id.totalScore);

                Log.w("fs", (name == null ? "null" : "not"));

                Log.i("entry:", currentPlayer[0]);

                rank.setText((position + 1) + "");
                name.setText(currentPlayer[0]);
                win.setText(currentPlayer[1]);
                loss.setText(currentPlayer[2]);
                tie.setText(currentPlayer[3]);
                total.setText(currentPlayer[4]);
            }

            return v;
        }
    }
}
