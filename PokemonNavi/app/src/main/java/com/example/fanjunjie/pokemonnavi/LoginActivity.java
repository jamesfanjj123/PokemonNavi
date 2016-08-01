package com.example.fanjunjie.pokemonnavi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

   // LoginButton loginButton;
    CallbackManager callbackManager;
    TextView textView;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Toast.makeText(LoginActivity.this,"current "+AccessToken.getCurrentAccessToken(),Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_login);


      //  textView= (TextView) findViewById(R.id.textView);

//

        profileTracker =new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                if(oldProfile!= null){
//
                    Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                    startActivity(intent);
                }

            }
        };






    }


    public void FacebookLogin(View view) {


        callbackManager=CallbackManager.Factory.create();

       // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

               // Toast.makeText(LoginActivity.this,loginResult.toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,"Login canceled",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this,"Login error",Toast.LENGTH_LONG).show();

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
        profileTracker.stopTracking();
        LoginManager.getInstance().logOut();
    }

    public void Gogest(View view) {

        Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
        startActivity(intent);

    }
}
