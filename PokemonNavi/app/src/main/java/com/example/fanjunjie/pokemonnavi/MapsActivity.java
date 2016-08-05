package com.example.fanjunjie.pokemonnavi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    GoogleMap googleMapvar;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    Location location;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    ProfilePictureView profilePictureView;
    TextView textView;
    Button button;
    PlaceAutocompleteFragment autocompleteFragment;
    Boolean teleport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_maps);
        autocompleteFragment= (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //get id and username
        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        textView = (TextView) findViewById(R.id.Welcome);
        button = (Button) findViewById(R.id.logout_button);

        Bundle bundle = getIntent().getExtras();


        if(bundle!=null){

            profilePictureView.setProfileId(bundle.getString("id"));

            textView.setText("Welcome "+bundle.getString("name"));
        }else {


            textView.setText("Welcome Guest!!!");
            button.setText("Try log in ");

        }









        if (GoogleServiceAvailability()) {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
            mapFragment.getMapAsync(this);
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10 * 1000).setFastestInterval(1 * 1000);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    LatLng latLngselect= place.getLatLng();
                    Log.i("Latlong",place.getLatLng().toString());
                    teleport= Boolean.TRUE;
                    googleMapvar.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngselect,16));
                }


                @Override
                public void onError(Status status) {
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.i(TAG, "OnStart: Location update started");

    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
            Log.i(TAG, "onPause: Location updated removed");
        }
    }

    public boolean GoogleServiceAvailability() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (api.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapvar = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    public void setMapCameraToCurrent(View view) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        googleMapvar.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Location services not Available.");

            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Cant access your location");
            alert.setMessage("Please turn on Location access permission");
            alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alert.show();

            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(location==null)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.i("Still Null", "Doesnt have any value ");
        }
        else {

            Log.i("Not null", "Has some value: ");
            handleNewLocation(location);

        }

    }


    private void handleNewLocation(Location location) {
        Log.i(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);


           googleMapvar.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

            googleMapvar.addMarker(new MarkerOptions().position(latLng).title("Start").icon(BitmapDescriptorFactory.fromResource(R.drawable.myloc)));

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
        Log.i(TAG, "Location  changed.");
    }


    public void FacebookLogout(View view) {

        LoginManager.getInstance().logOut();

        this.finish();



    }

    public void addpokemon(View view) {
       final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.show();
        Button cancle= (Button) dialog.findViewById(R.id.button3);
        EditText pokename = (EditText) dialog.findViewById(R.id.editText);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button add = (Button) dialog.findViewById(R.id.button2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


     }
}
