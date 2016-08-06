package com.example.fanjunjie.pokemonnavi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by revanth on 8/5/16.
 */
public class SearchDatabase extends AsyncTask <String,Void,PokemonInfo>{
    PokemonInfo pokemonInfo;
    SQLiteDatabaseManager sqLiteDatabaseManager;
    URL url = null;
    Bitmap image = null;
    LatLng current;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sqLiteDatabaseManager= new SQLiteDatabaseManager(MapsActivity.getAppContext());
    }

    @Override
    protected PokemonInfo doInBackground(String... params) {
        pokemonInfo= sqLiteDatabaseManager.getpokeimage(params[0]);
        Log.d("info", pokemonInfo.pokename);
        Log.d("info", pokemonInfo.url);
        try {
            url= new URL(pokemonInfo.url);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemonInfo;
    }

    @Override
    protected void onPostExecute(PokemonInfo pokemonInfo) {
        if(MapsActivity.Flag)
        {
            current=MapsActivity.telLatlng;
        }
        else {
            current = MapsActivity.pLatlong;
        }
        MapsActivity.googleMapvar.addMarker(new MarkerOptions().position(current).title(pokemonInfo.pokename).icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(image, 350, 350, false))).draggable(true));
        Toast.makeText(MapsActivity.getAppContext(),pokemonInfo.pokename+" added to the map",Toast.LENGTH_SHORT).show();
    }
}
