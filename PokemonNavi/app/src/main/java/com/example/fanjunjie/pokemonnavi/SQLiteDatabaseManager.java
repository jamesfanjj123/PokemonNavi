package com.example.fanjunjie.pokemonnavi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fanjunjie on 8/3/16.
 */
public class SQLiteDatabaseManager {

    public class SqlHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "PokeApi";
        private static final int DATABASE_VERSION = 1;


        public SqlHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
