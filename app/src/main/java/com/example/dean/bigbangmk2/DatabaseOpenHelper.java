package com.example.dean.bigbangmk2;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by Dean on 15/05/2015.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {


    private static int version = 1;

    private static String TABLE_NAME = "HIGHSCORES";
    ContextWrapper context;

// All Query strings used by helper saves time etc

    private static final String INSERT_QUERY =
            "INSERT INTO " + TABLE_NAME + " (name, win, loss, tie, totalGames, winPercentage) " +
                    "VALUES (\'%s\', \'%s\',\'%s\', \'%s\', \'%s\', \'%s\');";

    private static final String GET_PLAYER_DATA =
            "SELECT * FROM " + TABLE_NAME + " WHERE name = '%s'";

    private static final String UPDATE_QUERY =
            "UPDATE " + TABLE_NAME + " SET name = '%s', win = %s, loss = %s, tie = %s, totalGames = %s, winPercentage = %s WHERE name = '%s'";

    private static final String SORT_DATABASE =
            "SELECT * FROM "+ TABLE_NAME + " ORDER BY winPercentage DESC";

    private static final String PLAYER_CHECK = "SELECT * FROM "+
            TABLE_NAME + " WHERE name = '%s'";

    public DatabaseOpenHelper(Context context) {
        super(context, TABLE_NAME, null, version);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        setup(db, context);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

// ####   Private functions ####

    private void setup(SQLiteDatabase database, ContextWrapper context) {
        if (!doesDatabaseExist(context, TABLE_NAME)) {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //Make sure there are no conflicts
            database.execSQL("CREATE TABLE " + TABLE_NAME +
                    " (name TEXT, win INT, loss INT, tie INT, totalGames INT, winPercentage INT);");
        }
    }
    private static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
      boolean check = true;
       try {
           File dbFile = context.getDatabasePath(dbName);
       }catch (NullPointerException e) {
           check = false;
       }
        return check;
    }



//    ####  Public functions ######

    public static void pastPlayerScore(SQLiteDatabase database, String name) {
        Cursor cursor = database.rawQuery(String.format(GET_PLAYER_DATA, name), null);
        cursor.moveToFirst();
       GameHub.win = cursor.getInt(1);
        GameHub.loss = cursor.getInt(2);
        GameHub.tie = cursor.getInt(3);
    }

    public static Cursor rankDatabase(SQLiteDatabase database){
        return database.rawQuery(SORT_DATABASE, null);
    }
    public static String getTableName() {
        return TABLE_NAME;
    }

    public static void addNewPlayer(SQLiteDatabase database, String name){
        int win = 0, loss = 0, totalGames = 0, tie = 0;
        float winPercentage = 0;
        database.execSQL(String.format(INSERT_QUERY, name, win, loss, tie, totalGames, winPercentage));
    }

    public static void upDatePlayer(SQLiteDatabase database){
        String name = GameHub.playerName;
        int win = GameHub.win;
        int loss = GameHub.loss;
        int tie = GameHub.tie;
        int totalGames = win + loss + tie;
        float rankvalue;
        int winlossValue = win + loss;
        if (totalGames != 0) {
            rankvalue = ((float) win / (float) winlossValue) * 100;
        } else
            rankvalue = win;
        Log.i("RANKVALUE", rankvalue + "");
        Log.i("QUERY", String.format(UPDATE_QUERY, name, win, loss, tie, totalGames, rankvalue, name));

        database.execSQL(String.format(UPDATE_QUERY, name, win, loss, tie, totalGames, rankvalue, name));
        rankDatabase(database);
    }

    public static boolean playerCheck (SQLiteDatabase database, String name){
        Cursor record = database.rawQuery(String.format(PLAYER_CHECK, name), null);
        Boolean check = false;
        if (record.moveToFirst()){
            check = true;
        }
        return check;
    }


}
