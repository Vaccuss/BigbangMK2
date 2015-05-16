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

//    Creates a new row (Defines the columns that are being inserted) Values (the columns values)
//    INSERT INTO "TABLE NAME" then next
    private static final String INSERT_QUERY =
            "INSERT INTO" +TABLE_NAME+ "(name TEXT, win INT, loss INT, totalGames INT, winPercentage REAL) " +
                    "VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\');";

//    UPDATE table_name
//    SET column1=value1,column2=value2,..
//    WHERE some_column=some_value;.

    private static final String UPDATA_QUERY =
            "UPDATE" + TABLE_NAME + "SET name = %s, win = %s, loss = %s, totalGames = %s winPercentage = %s WHERE %s";
    private static final String SORT_DATABASE = "SELECT * FROM"+ TABLE_NAME + " ORDER BY winPercentage DESC";


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
                    " (name TEXT, win INT, loss INT, totalGames INT, winPercentage INT);");
        }
    }
    private static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private static void rankDatabase(SQLiteDatabase database){
        database.execSQL(SORT_DATABASE);
    }

//    ####  Public functions ######

    public static void addNewPlayer(SQLiteDatabase database, String name){
        int win = 0, loss = 0, totalGames = 0;
        float winPercentage = 0;
        database.execSQL(String.format(INSERT_QUERY, name, win, loss, totalGames, winPercentage));

    }

    public static void upDatePlayer(SQLiteDatabase database, String name, int win, int loss){
        int totalGames = win + loss;
        int rankvalue = win / totalGames * 100;
        database.execSQL(String.format(UPDATA_QUERY, name, win, loss, totalGames, rankvalue));
        rankDatabase(database);
    }




}
