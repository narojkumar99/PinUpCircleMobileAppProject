package com.pinupcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alium.nibo.autocompletesearchbar.NiboAutocompleteSVProvider;
import com.alium.nibo.autocompletesearchbar.NiboPlacesAutoCompleteSearchView;
import com.alium.nibo.autocompletesearchbar.NiboSearchSuggestionItem;
import com.alium.nibo.models.NiboSelectedOriginDestination;
import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.origindestinationpicker.NiboOriginDestinationPickerActivity;
import com.alium.nibo.placepicker.NiboPlacePickerActivity;
import com.alium.nibo.utils.NiboConstants;
import com.alium.nibo.utils.NiboStyle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

public class MainActivity extends AppCompatActivity implements NiboAutocompleteSVProvider, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private NiboPlacesAutoCompleteSearchView mAutocompletesearchbar;
    private Button mLocationPicker;
    private Button mOriginDestinationPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        initView();

    }


    private void launchPickerFragment() {
        Intent intent = new Intent(this, NiboPlacePickerActivity.class);
        NiboPlacePickerActivity.NiboPlacePickerBuilder config = new NiboPlacePickerActivity.NiboPlacePickerBuilder()
                .setSearchBarTitle("Search for an area")
                .setConfirmButtonTitle("Pick here bish")
                .setMarkerPinIconRes(R.drawable.ic_map_marker_black_36dp)
                .setStyleEnum(NiboStyle.DEFAULT);
        //.setStyleFileID(R.raw.retro);
        NiboPlacePickerActivity.setBuilder(config);
        startActivityForResult(intent, 300);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 300) {
            NiboSelectedPlace selectedPlace = data.getParcelableExtra(NiboConstants.SELECTED_PLACE_RESULT);
            Toast.makeText(this, selectedPlace.getPlaceAddress(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error getting results", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onHomeButtonClicked() {

    }

    @Override
    public NiboPlacesAutoCompleteSearchView.SearchListener getSearchListener() {
        return new NiboPlacesAutoCompleteSearchView.SearchListener() {

            @Override
            public void onSearchEditOpened() {


            }

            @Override
            public void onSearchEditClosed() {

            }


            @Override
            public boolean onSearchEditBackPressed() {

                return false;
            }

            @Override
            public void onSearchExit() {

            }

            @Override
            public void onSearchTermChanged(String term) {


            }

            @Override
            public void onSearch(String string) {

            }

            @Override
            public boolean onSuggestion(NiboSearchSuggestionItem niboSearchSuggestionItem) {
                Toast.makeText(MainActivity.this, "PLACE NAME:" + niboSearchSuggestionItem.getFullTitle() + " PLACE ID: " + niboSearchSuggestionItem.getPlaceID(), Toast.LENGTH_SHORT).show();
                mAutocompletesearchbar.closeSearch();
                return false;
            }

            @Override
            public void onSearchCleared() {

            }

        };
    }

    @Override
    public boolean getShouldUseVoice() {
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
    private void initView() {
        /*mAutocompletesearchbar = (NiboPlacesAutoCompleteSearchView) findViewById(R.id.autocompletesearchbar);
        mLocationPicker = (Button) findViewById(R.id.location_picker);
        mOriginDestinationPicker = (Button) findViewById(R.id.origin_destination_picker);
        mAutocompletesearchbar.setmProvider(this);
        mLocationPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPickerFragment();
            }
        });*/


    }
}
