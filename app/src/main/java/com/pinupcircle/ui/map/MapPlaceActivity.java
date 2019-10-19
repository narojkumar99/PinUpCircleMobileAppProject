package com.pinupcircle.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pinupcircle.R;
import com.pinupcircle.ui.home.DashboardActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    String address = "";
    Button addPinCode;
    TextView currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_place);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        initFields();

        createLocationRequest();

        getMapAsync();

        setUpGoogleApiClient();

        setupLocationCallBack();

        setResult(Activity.RESULT_CANCELED);

        initialisePlaces();

        addPinCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackLocation();

            }
        });

    }

    private void initFields() {

        addPinCode = findViewById(R.id.addPinCode);
        currentAddress = findViewById(R.id.currentAddress);
    }

    private void setUpGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                startLocationUpdates();
                            }
                            @Override
                            public void onConnectionSuspended(int i) {
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                            }
                        }
                )
                .build();
    }

    protected void startLocationUpdates() {
        com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null);
    }

    private void setupLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResultLastSaved == null) {
                    locationResultLastSaved = locationResult;
                   /* Toast.makeText(MapPlaceActivity.this, "" + locationResult.getLastLocation().getLatitude(),
                            Toast.LENGTH_SHORT).show();
*/
                    animateGoogleMapCurLocation(locationResult.getLocations().get(0));
                    addToMarker(new LatLng(
                            locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()
                    ));
                    settUpMarkerDragListener();
                }
                if (
                        locationName.equalsIgnoreCase("")
                ) {
                    getAddress(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()));
                }
            }
        };
    }

    private void addToMarker(LatLng latLng) {
        if (latLng == null)
            return;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(
                new LatLng(
                        latLng.latitude,
                        latLng.longitude
                )
        );
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_icon));
        markerOptions.anchor(0.5f, 0.5f);
        marker = map.addMarker(markerOptions);
        marker.setFlat(false);
        marker.setDraggable(true);
    }

    private void animateGoogleMapCurLocation(android.location.Location currentLocation) {
        //Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        //getAddressNameFromGeoCoder(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()));
        if (map == null)
            return;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(
                        new LatLng(currentLocation.getLatitude(),
                                currentLocation.getLongitude())
                )
                .bearing(
                        currentLocation.getBearing()
                )
                .zoom(18)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }
    private void settUpMarkerDragListener() {
        if (map == null)
            return;
        map.setOnMarkerDragListener(
                new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        getAddress(marker.getPosition());

                    }
                }
        );
    }
    private void getAddress(LatLng latLng) {
        getAddressNameFromGeoCoder(latLng);
    }

    private String getAddressNameFromGeoCoder(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            currentAddress.setText(""+address);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private void initialisePlaces() {

        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(
                getApplicationContext(),
                apiKey
        );

        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteSupportFragment =
                ((AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment));

        autocompleteSupportFragment
                .setPlaceFields(
                        Arrays.asList(
                                Place.Field.ID,
                                Place.Field.NAME,
                                Place.Field.ADDRESS,
                                Place.Field.LAT_LNG

                        )
                );

        autocompleteSupportFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        //Toast.makeText(MapPlaceActivity.this, "Place"+place.getAddress(), Toast.LENGTH_SHORT).show();
                        gotNewLocation(
                                place.getName(),
                                place.getLatLng()

                        );
                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(MapPlaceActivity.this, "Error while getting your location.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    @SuppressLint("RestrictedApi")
    private void gotNewLocation(String locationName, LatLng latLng) {
        //addPinCode.setVisibility(View.VISIBLE);
        this.locationName = locationName;
        Toast.makeText(this, "Pin "+locationName, Toast.LENGTH_SHORT).show();
        getAddressNameFromGeoCoder(latLng);
        //currentAddress.setText(""+locationName);
        if (map == null)
            return;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(
                        latLng
                )
                .zoom(19)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onCancel() {
                    }
                });
        marker.setPosition(latLng);
    }

    private void getMapAsync() {
        //noinspection ConstantConditions
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        if (ActivityCompat.checkSelfPermission(MapPlaceActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(MapPlaceActivity.this,
                                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        MapPlaceActivity.this.map = googleMap;
                        googleMap.setPadding(0, 0, 0, 0);
                        googleMap.setMyLocationEnabled(true);
                        //settUpMarkerDragListener();
                    }
                }
        );
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        /*
        smallest displacement is not set as the location update is required more frequently even if the
        passenger is standing still.
         */
//        locationRequest.setSmallestDisplacement(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapPlaceActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        sendEx.printStackTrace();
                    }
                }
            }
        });
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPressed();
    }

    private void backPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        stopLocationUpdates();
    }


    protected void stopLocationUpdates() {
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void sendBackLocation() {
        //Toast.makeText(this, ""+locationName, Toast.LENGTH_SHORT).show();
        if (marker == null)
            finish();
        Intent mnIntent = new Intent(MapPlaceActivity.this, DashboardActivity.class);
          startActivity(mnIntent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
       /* Intent returnIntent = new Intent();
        returnIntent.putExtra(LAT_LNG,
                marker.getPosition().latitude + "," + marker.getPosition().longitude
        );

        returnIntent.putExtra(LOCATION_NAME, locationName);
        setResult(Activity.RESULT_OK, returnIntent);*/
        //finish();

    }

}
