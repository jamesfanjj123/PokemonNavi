package com.example.fanjunjie.pokemonnavi;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by revanth on 8/5/16.
 */
public class SearchDatabase extends AsyncTask <String,Void,PokemonInfo>{
    PokemonInfo pokemonInfo;
    URL url = null;
    Bitmap image = null;
    LatLng current;
    LatLng fin;
    Marker pokemarker;
SQLiteDatabaseManager sqLiteDatabaseManager= new SQLiteDatabaseManager(MyApplication.getContext());
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected PokemonInfo doInBackground(String... params) {
        pokemonInfo= sqLiteDatabaseManager.getpokeimage(params[0]);
        if(pokemonInfo==null)
        {
            return null;
        }
        else {

            Log.d("info", pokemonInfo.pokename);
            Log.d("info", pokemonInfo.url);
            try {
                url = new URL(pokemonInfo.url);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pokemonInfo;
        }
    }

    @Override
    protected void onPostExecute(PokemonInfo pokemonInfovar) {
        pokemonInfo=pokemonInfovar;
        if (pokemonInfo == null) {
            AlertDialog alert = new AlertDialog.Builder(MapsActivity.getAppContext()).create();
            alert.setTitle("Sorry ");
            alert.setMessage("Enter Valied pokemon name");
            alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alert.show();
        } else {
//            if (MapsActivity.Flag) {
//                current = MapsActivity.telLatlng;
//            } else {
//                current = MapsActivity.pLatlong;
//            }
            current=MapsActivity.googleMapvar.getCameraPosition().target;
            fin=current;
            pokemarker= MapsActivity.googleMapvar.addMarker(new MarkerOptions().position(current).title(pokemonInfo.pokename).icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(image, 350, 350, false))).draggable(true).zIndex(100000));
            Toast.makeText(MapsActivity.getAppContext(), pokemonInfo.pokename + " added to the map", Toast.LENGTH_SHORT).show();
            MapsActivity.Buttonvisibility(Boolean.TRUE);
            MapsActivity.add.setVisibility(View.INVISIBLE);
            MapsActivity.googleMapvar.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    fin=marker.getPosition();
                    Log.i("Markloc", fin.toString());

                }
            });

            MapsActivity.cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapsActivity.add.setVisibility(View.VISIBLE);
                    pokemarker.remove();
                    MapsActivity.Buttonvisibility(Boolean.FALSE);

                }
            });
            MapsActivity.finalize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapsActivity.add.setVisibility(View.VISIBLE);
                   if(sqLiteDatabaseManager.insertlog(MapsActivity.userID,pokemonInfo.pokename,pokemonInfo.url,fin.latitude,fin.longitude))
                    {
                        Toast.makeText(MapsActivity.getAppContext(), pokemonInfo.pokename + " added to the Database", Toast.LENGTH_SHORT).show();
                        MapsActivity.Buttonvisibility(Boolean.FALSE);
                        pokemarker.setDraggable(false);
                    }
                    else {
                       Toast.makeText(MapsActivity.getAppContext(), pokemonInfo.pokename + " Error!! Not added to the Database", Toast.LENGTH_SHORT).show();
                       MapsActivity.Buttonvisibility(Boolean.FALSE);

                   }


                }
            });
        }
    }
}

