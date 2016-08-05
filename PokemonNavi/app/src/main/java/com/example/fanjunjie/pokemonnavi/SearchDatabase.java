package com.example.fanjunjie.pokemonnavi;

import android.os.AsyncTask;

/**
 * Created by revanth on 8/5/16.
 */
public class SearchDatabase extends AsyncTask <String,Void,PokemonInfo>{
    @Override
    protected PokemonInfo doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(PokemonInfo pokemonInfo) {
        super.onPostExecute(pokemonInfo);
    }
}
