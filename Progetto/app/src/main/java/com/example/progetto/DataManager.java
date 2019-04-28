package com.example.progetto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//si crea la classe java per gestire il database
public class DataManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_MATERIA = "materia";
    public static final String TABLE_ROW_ORARIO = "orario";

    private static final String DB_NAME = "materia_ora_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_M_AND_O = "materia_e_ora";

    public DataManager (Context context){
        super (context, DB_NAME, null, DB_VERSION);
        CustomSLQiteOpenHelper helper = new CustomSLQiteOpenHelper (context);
        db = helper.getWritableDatabase();
    }
    //metodi di salvataggio eliminazione ricerca e "visualizza tutto" usano delle stringhe che sql elabora
    //alcuni utilizzano il tipo cursore e restituiscono un cursore alla chiamante che lo userà per "puntare" i dati
    public void insert(String materia, String ora){
        String query = "INSERT INTO " + TABLE_M_AND_O + " (" + TABLE_ROW_MATERIA + ", " + TABLE_ROW_ORARIO + ") " + "VALUES (" + "'" + materia + "'" + ", " + "'" + ora + "'" + ");";
        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    public Cursor search(String materia){
        String query = "SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_MATERIA + ", " + TABLE_ROW_ORARIO + " from " + TABLE_M_AND_O + " WHERE " + TABLE_ROW_MATERIA + " = '" + materia + "';";
        Log.i("searchName() =", query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public void delete(String materia){
        String query = "DELETE FROM " + TABLE_M_AND_O + " WHERE " + TABLE_ROW_MATERIA + " = '" + materia + "';";
        db.execSQL(query);
    }

    public Cursor selectAll (){
        Cursor c = db.rawQuery("SELECT* " + "FROM " + TABLE_M_AND_O, null);
        return c;
    }

    //Questi metodi servono per utilizzare l'estenzione SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private class CustomSLQiteOpenHelper extends SQLiteOpenHelper{
        public CustomSLQiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String newTableQueryString = "create table " + TABLE_M_AND_O + " (" + TABLE_ROW_ID + "integer primary key autoincrement not null, " + TABLE_ROW_MATERIA + " text not null, " + TABLE_ROW_ORARIO + " text not null);";
            db.execSQL(newTableQueryString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
