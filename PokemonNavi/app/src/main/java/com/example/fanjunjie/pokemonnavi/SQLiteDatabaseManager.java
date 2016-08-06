package com.example.fanjunjie.pokemonnavi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fanjunjie on 8/3/16.
 */
public class SQLiteDatabaseManager extends SQLiteOpenHelper{



        private static final String DATABASE_NAME = "PokeDB";
        private static final int DATABASE_VERSION = 1;
    private static final String[] COLUMNS = {"id","name","picurl"};

        public SQLiteDatabaseManager(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL( "CREATE TABLE " + "PokeData" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT ," + "name" + " VARCHAR(255)," + "picurl" + " VARCHAR(255)"+ ")");
          //  db.execSQL("CREATE TABLE " + "UserData" + "(" + "id" + " VARCHAR(255) ," + "pokename" + " VARCHAR(255)," + "finddate" + " DATETIME"+ ")");
          //  db.execSQL("CREATE TABLE " + "LocationData" + "(" + "name" + " VARCHAR(255),"+ "lon" + " VARCHAR(255)," + "lat" + " VARCHAR(255)"+ ")");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


        public boolean insertPokeData(String name, String url){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",name);
            contentValues.put("picurl",url);
            db.insert("PokeData",null,contentValues);

            return true;

        }

           public PokemonInfo getpokeimage(String pokename)
          {
              PokemonInfo pokemonInfo= new PokemonInfo();
              SQLiteDatabase db=this.getReadableDatabase();
              Cursor cursor=db.query("PokeData",COLUMNS,"name = ?",new String[] {pokename},null,null,null,null);
              cursor.moveToFirst();
              pokemonInfo.pokedeid=cursor.getInt(cursor.getColumnIndex("id"));
              pokemonInfo.pokename=cursor.getString(cursor.getColumnIndex("name"));
              pokemonInfo.url=cursor.getString(cursor.getColumnIndex("picurl"));
              return  pokemonInfo;

          }


    }
