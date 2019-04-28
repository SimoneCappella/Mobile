package com.example.progetto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_M = "m";
    public static final String TABLE_ROW_O = "o";

    private static final String DB_NAME = "m_o_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_M_AND_O = "m_and_o";

    public DataManager (Context context){
        super(context, DB_NAME, null, DB_VERSION);
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper (context);
        db = helper.getWritableDatabase();
    }

    public void insert(String m, String o){
        String query = "INSERT INTO " + TABLE_M_AND_O + " (" + TABLE_ROW_M + ", " + TABLE_ROW_O + ") " + "VALUES (" + "'" + m + "'" + ", " + "'" + o + "'" + ");";
        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    public void delete(String m){
        String query = "DELETE FROM " + TABLE_M_AND_O+ " WHERE " + TABLE_ROW_M + " = '" + m + "';";
        db.execSQL(query);
    }

    public Cursor selectAll(){
        Cursor c = db.rawQuery("SELECT* " + "FROM " + TABLE_M_AND_O, null);
        return c;
    }

    public Cursor searchM(String m){
        String query = "SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_M + ", " + TABLE_ROW_O + " from " + TABLE_M_AND_O + " WHERE " + TABLE_ROW_M + " = '" + m + "';";
        Log.i("searchM() = ", query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
        public CustomSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            String newTableQueryString = "create table " + TABLE_M_AND_O + " (" + TABLE_ROW_ID + " integer primary key autoincrement not null, " + TABLE_ROW_M + " text not null, " + TABLE_ROW_O + " text not null);";
            db.execSQL(newTableQueryString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }
    }
}

