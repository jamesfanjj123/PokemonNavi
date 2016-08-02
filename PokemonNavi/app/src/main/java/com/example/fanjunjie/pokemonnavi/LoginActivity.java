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
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {


    CallbackManager callbackManager;


    ProfileTracker mProfileTracker;

   // ProfilePictureView profilePictureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());



        setContentView(R.layout.activity_login);

       // profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicturetest);

        // first check if user already log in, if logged in with info goto second Activity

        if(Profile.getCurrentProfile()!=null){

            //Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
            // startActivity(intent);

            //Toast.makeText(LoginActivity.this,"already Login",Toast.LENGTH_LONG).show();


        }

            // track Profile change
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                    Profile.setCurrentProfile(currentProfile);
                    if(currentProfile!=null){

                       //SharedPreferences.Editor editor = (SharedPreferences.Editor) getSharedPreferences("MyFile",MODE_PRIVATE);

                        //editor.putString("id",currentProfile.getId());
                        //editor.putString("name",currentProfile.getName());
                        //editor.commit();

                      //  profilePictureView.setProfileId(currentProfile.getId());
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


        //SharedPreferences.Editor editor = (SharedPreferences.Editor) getSharedPreferences("MyFile",MODE_PRIVATE);

        //editor.putString("id",null);
        //editor.putString("name",null);
        //editor.commit();



        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);



    }



}