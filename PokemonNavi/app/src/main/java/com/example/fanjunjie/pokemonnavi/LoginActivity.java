package com.example.fanjunjie.pokemonnavi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    // LoginButton loginButton;
    CallbackManager callbackManager;
    TextView textView;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker mProfileTracker;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());



        setContentView(R.layout.activity_login);


          textView= (TextView) findViewById(R.id.textView);

// first check if user already log in, if logged in with info goto second Activity

        if(Profile.getCurrentProfile()!=null){
            //Toast.makeText(LoginActivity.this,"have file",Toast.LENGTH_LONG).show();

           // textView.setText(Profile.getCurrentProfile().getFirstName());
            Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
             startActivity(intent);


        }

// track Profile change
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                    Profile.setCurrentProfile(currentProfile);
                    if(currentProfile==null){

                        textView.setText("loged out");
                    }else{

                        textView.setText(Profile.getCurrentProfile().getFirstName());


                    }
                    //Toast.makeText(LoginActivity.this,"creat one profile",Toast.LENGTH_LONG).show();

                }
            };


            //textView.setText(Profile.getCurrentProfile().getFirstName());
            //Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
            //startActivity(intent);





    }


    public void FacebookLogin(View view) {


        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {




                //Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                // startActivity(intent);
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
        //LoginManager.getInstance().logOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LoginManager.getInstance().logOut();

    }

    public void Gogest(View view) {

        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);

    }



}