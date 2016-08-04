package com.example.fanjunjie.pokemonnavi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fanjunjie on 8/3/16.
 */
public class SQLiteDatabaseManager {

    public class SqlHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "PokeDB";
        private static final int DATABASE_VERSION = 1;


        public SqlHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL( "CREATE TABLE " + "PokeData" + "(" + "id" + " INTEGER ," + "name" + " VARCHAR(255)," + "picurl" + " VARCHAR(255)"+ ")");
            db.execSQL("CREATE TABLE " + "UserData" + "(" + "id" + " VARCHAR(255) ," + "pokename" + " VARCHAR(255)," + "finddate" + " DATETIME"+ ")");
            db.execSQL("CREATE TABLE " + "LocationData" + "(" + "name" + " VARCHAR(255),"+ "lon" + " VARCHAR(255)," + "lat" + " VARCHAR(255)"+ ")");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
