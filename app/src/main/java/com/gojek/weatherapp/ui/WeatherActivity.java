package com.gojek.weatherapp.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gojek.weatherapp.R;
import com.gojek.weatherapp.WeatherApplication;
import com.gojek.weatherapp.helper.WeatherHelperClass;
import com.gojek.weatherapp.model.ApiResponse;
import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.utils.WeatherUtility;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import static com.gojek.weatherapp.utils.WeatherConstants.API_KEY;
import static com.gojek.weatherapp.utils.WeatherConstants.PERMISSION_REQUEST_LOCATION;
import static java.util.Locale.getDefault;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {


    @Inject
    ViewModelFactory viewModelFactory;
    WeatherViewModel weatherViewModel;
    private ProgressBar progressBar;
    @Nullable
    private LocationManager mLocationManager;
    private TextView tempVal;
    private TextView tempCity;
    private TextView tempErr;
    private Button tempErrBtn;
    private View mLayout;
    private String mCachedCity;
    private boolean isApiCalledOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordiantor_layout);
        ((WeatherApplication) getApplication()).getAppComponent().doInjection(this);
        progressBar = findViewById(R.id.progressBar);
        tempVal = findViewById(R.id.temperature_val);
        tempCity = findViewById(R.id.temperature_city);
        tempErr = findViewById(R.id.temperature_error);
        tempErrBtn = findViewById(R.id.temperature_retry);
        mLayout = findViewById(R.id.parent_layout);

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        showLocation();
    }

    private void callWeatherApi(String locality) {
        weatherViewModel.forcastResponse().observe(this, this::consumeResponse);
        weatherViewModel.loadForcast(API_KEY, locality, 4);
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                showViewsForLoading();


                break;

            case SUCCESS:
                renderUI(apiResponse.data);
                break;

            case ERROR:
                showViewsForFailure();
                break;

            default:
                break;
        }
    }

    private void showViewsForLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tempErrBtn.setVisibility(View.GONE);
        tempErr.setVisibility(View.GONE);
        mLayout.setBackgroundColor(getResources().getColor(R.color.grey_f5f6f7));
    }

    private void showViewsForFailure() {
        progressBar.setVisibility(View.GONE);
        tempErr.setVisibility(View.VISIBLE);
        tempVal.setVisibility(View.GONE);
        tempCity.setVisibility(View.GONE);
        tempErrBtn.setVisibility(View.VISIBLE);
        tempErrBtn.setOnClickListener(this);
        mLayout.setBackgroundColor(getResources().getColor(R.color.red_e85959));
    }

    private void showViewsForSuccess(WeatherResponse data) {

        progressBar.clearAnimation();
        progressBar.setVisibility(View.GONE);
        tempVal.setVisibility(View.VISIBLE);
        tempCity.setVisibility(View.VISIBLE);
        tempVal.setText(String.format("%s%s", String.valueOf(Math.round(data.getCurrent().getTempC())), (char) 0x00B0));
        tempCity.setText(data.getLocation().getName());
    }

    private void renderUI(@NonNull WeatherResponse data) {
        WeatherHelperClass weatherHelperClass = new WeatherHelperClass();
        showViewsForSuccess(data);
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setAutoMeasureEnabled(true);
        ForcastDisplayAdapter adapter = new ForcastDisplayAdapter(this, weatherHelperClass.processWeatherResponse(data));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Geocoder geocoder = new Geocoder(getApplicationContext(), getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && !addresses.isEmpty() && !isApiCalledOnce) {
                mCachedCity = addresses.get(0).getLocality();
                callWeatherApi(addresses.get(0).getLocality());
                isApiCalledOnce = true;

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.temperature_retry:
                callWeatherApi(mCachedCity);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.location_permission_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, mLocationListener);
            } else {
                Snackbar.make(mLayout, R.string.location_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Snackbar.make(mLayout, R.string.location_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(WeatherActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_LOCATION);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, R.string.location_unavailable, Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        }
    }

    private void showLocation() {
        if (!WeatherUtility.checkInternetConnection(this)) {
            Snackbar.make(mLayout, R.string.no_network_connection, Snackbar.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, mLocationListener);
            } else {
                requestLocationPermission();

            }
        }

    }


}
