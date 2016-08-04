package com.example.fanjunjie.pokemonnavi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {


    CallbackManager callbackManager;


    ProfileTracker mProfileTracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        Intent pkapi = new Intent(this,PokemonApiInitService.class);
        startService(pkapi);


        if(Profile.getCurrentProfile()!=null){


            Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
            intent.putExtra("id",Profile.getCurrentProfile().getId());
            intent.putExtra("name",Profile.getCurrentProfile().getName());
            startActivity(intent);


        }



        // track Profile change
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                    Profile.setCurrentProfile(currentProfile);
                    if(currentProfile!=null){


                        Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                        intent.putExtra("id",currentProfile.getId());
                        intent.putExtra("name",currentProfile.getName());

                        startActivity(intent);


                    }
                }
            };


    }


    public void FacebookLogin(View view) {


        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login canceled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_LONG).show();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProfileTracker.stopTracking();
    }



    public void Gogest(View view) {

        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);



    }



}