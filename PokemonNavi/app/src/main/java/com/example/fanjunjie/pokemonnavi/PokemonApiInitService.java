package com.example.fanjunjie.pokemonnavi;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fanjunjie on 8/3/16.
 */
public class PokemonApiInitService extends IntentService {

    public static final String SERVER_URL="https://pokeapi.co/api/v2/pokemon/";


    public PokemonApiInitService() {
        super("Api service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        for(int i=0; i<722;i++) {

            try {
                URL url = new URL(SERVER_URL + i);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

                String json = bufferedReader.readLine();

                JSONObject jsonObject = new JSONObject(json);

                String pokeName = jsonObject.getString("name");

                Log.d("poke name", pokeName);

                JSONObject jsonsprites = jsonObject.getJSONObject("sprites");

                String picUrl = jsonsprites.getString("front_default");

                Log.d("url ", picUrl);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        stopSelf();


    }
}
