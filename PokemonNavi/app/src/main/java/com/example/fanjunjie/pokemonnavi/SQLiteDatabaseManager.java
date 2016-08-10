package com.example.fanjunjie.pokemonnavi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Created by fanjunjie on 8/3/16.
 */
public class SQLiteDatabaseManager extends SQLiteOpenHelper{



        private static final String DATABASE_NAME = "PokeDB3";
        private static final int DATABASE_VERSION = 1;
    private static final String[] COLUMNS = {"id","name","picurl"};

        public SQLiteDatabaseManager(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL( "CREATE TABLE " + "PokeData" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT ," + "name" + " VARCHAR(255)," + "picurl" + " VARCHAR(255)"+ ")");
            db.execSQL("CREATE TABLE " + "PokeLog" + "("+ "Logid" + " INTEGER PRIMARY KEY AUTOINCREMENT ," + "Userid" + " VARCHAR(255) ," + "pokename" + " VARCHAR(255)," +"lat" + " DOUBLE,"+"lon" + " DOUBLE" +")");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }


        public boolean insertPokeData(String name, String url){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",name);
            contentValues.put("picurl",url);
            db.insert("PokeData","Guest",contentValues);

            return true;

        }

        public boolean insertlog(String uname,String pokname,Double lat,Double lng)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Userid",uname);
            contentValues.put("pokename",pokname);
           contentValues.put("lat",lat);
            contentValues.put("lon",lng);
            db.insert("PokeLog","Guest",contentValues);

            return true;
        }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM  PokeData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

           @Nullable
           public PokemonInfo getpokeimage(String pokename)
          {
              PokemonInfo pokemonInfo= new PokemonInfo();
              SQLiteDatabase db=this.getReadableDatabase();
              Cursor cursor=db.query("PokeData",COLUMNS,"name = ?",new String[] {pokename},null,null,null,null);
              if(cursor!=null && cursor.getCount()>0) {
                  cursor.moveToFirst();
                  pokemonInfo.pokedeid = cursor.getInt(cursor.getColumnIndex("id"));
                  pokemonInfo.pokename = cursor.getString(cursor.getColumnIndex("name"));
                  pokemonInfo.url = cursor.getString(cursor.getColumnIndex("picurl"));
                  return pokemonInfo;
              }

                  return null;

          }


    }
