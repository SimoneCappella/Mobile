package com.example.progetto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_C = "c";
    public static final String TABLE_ROW_M = "m";
    public static final String TABLE_ROW_O = "o";
    public static final String TABLE_ROW_A = "a";

    private static final String DB_NAME = "c_m_o_a_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_C_M_AND_O_AND_A = "c_m_and_o_and_a";

    public DataManager (Context context){
        super(context, DB_NAME, null, DB_VERSION);
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper (context);
        db = helper.getWritableDatabase();
    }

    public void insert(String m, String o, String a, String c){
        String query = "INSERT INTO " + TABLE_C_M_AND_O_AND_A + " (" + TABLE_ROW_C + ", " + TABLE_ROW_M + ", " + TABLE_ROW_O + ", " + TABLE_ROW_A + ") " + "VALUES (" + "'" + c + "'" + ", " + "'" + m + "'" + ", " + "'" + o + "'" + ", " + "'" + a + "'" + ");";
        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    public void delete(String m){
        String query = "DELETE FROM " + TABLE_C_M_AND_O_AND_A + " WHERE " + TABLE_ROW_M + " = '" + m + "';";
        db.execSQL(query);
    }

    public Cursor selectAll(){
        Cursor c = db.rawQuery("SELECT* " + "FROM " + TABLE_C_M_AND_O_AND_A , null);
        return c;
    }

    public Cursor searchByCode(String co){
        String query = "SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_C + ", " + TABLE_ROW_M + ", " + TABLE_ROW_O + ", " + TABLE_ROW_A + " from " + TABLE_C_M_AND_O_AND_A + " WHERE " + TABLE_ROW_C + " = '" + co + "';";
        Log.i("searchById() = ", query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public void deleteByCode(String co){
        String query = "DELETE FROM " + TABLE_C_M_AND_O_AND_A + " WHERE " + TABLE_ROW_C + " = '" + co + "';";
        db.execSQL(query);
    }

    public Cursor searchM(String m){
        String query = "SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_C + ", " + TABLE_ROW_M + ", " + TABLE_ROW_O + ", " + TABLE_ROW_A + " from " + TABLE_C_M_AND_O_AND_A + " WHERE " + TABLE_ROW_M + " = '" + m + "';";
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
            String newTableQueryString = "create table " + TABLE_C_M_AND_O_AND_A + " (" + TABLE_ROW_ID + " integer primary key autoincrement not null, " + TABLE_ROW_C + " text not null, " + TABLE_ROW_M + " text not null, " + TABLE_ROW_O + " text not null, " + TABLE_ROW_A + " text not null);";
            db.execSQL(newTableQueryString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }
    }




    public void cancellaLuogo(Context context, String nome) {

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            String[] args = new String[]{nome};
            db.delete(DataManager.TABLE_ROW_A, DataManager.TABLE_ROW_ID + "=?", args);

            db.close();
        } catch (SQLiteException e) {
            db.close();
            e.printStackTrace();
        }
    }
}

