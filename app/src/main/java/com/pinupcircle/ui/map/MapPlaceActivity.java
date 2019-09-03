package com.pinupcircle.ui.map;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.pinupcircle.R;

public class MapPlaceActivity extends AppCompatActivity {

    private static final int REQUEST_CHECK_SETTINGS = 436;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private LocationCallback locationCallback;
    private GoogleMap map;

    private LocationResult locationResultLastSaved;
    private String locationName = "";
    private Marker marker;

    public static final String LAT_LNG = "LAT_LNG";
    public static final String LOCATION_NAME = "LOCATION_NAME ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_place);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

    }
    private boolean isGooglePlayServicesAvailable() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(this, status, 0).show();
            return false;
        }
    }

}
