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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gojek.weatherapp.R;
import com.gojek.weatherapp.WeatherApplication;
import com.gojek.weatherapp.helper.WeatherHelperClass;
import com.gojek.weatherapp.model.ApiResponse;
import com.gojek.weatherapp.model.WeatherResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static java.util.Locale.*;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {


    @Inject
    ViewModelFactory viewModelFactory;
    WeatherViewModel weatherViewModel;
    private ProgressBar progressBar;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordiantor_layout);
        ((WeatherApplication) getApplication()).getAppComponent().doInjection(this);
        progressBar = findViewById(R.id.progressBar);
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        weatherViewModel.forcastResponse().observe(this, this::consumeResponse);
        weatherViewModel.loadForcast("69e792543e624e7cb11180545192806", "Gurgaon", 4);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, mLocationListener);


    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Toast.makeText(WeatherActivity.this, getResources().getString(R.string.loading), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                progressBar.setVisibility(View.GONE);
                renderUI(apiResponse.data);
                Toast.makeText(WeatherActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                break;

            case ERROR:
                progressBar.setVisibility(View.GONE);

                Toast.makeText(WeatherActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void renderUI(WeatherResponse data) {
        WeatherHelperClass weatherHelperClass = new WeatherHelperClass();
        ((TextView) findViewById(R.id.temperature_val)).setText(String.valueOf(data.getCurrent().getTempC()));
        ((TextView) findViewById(R.id.temperature_city)).setText(data.getLocation().getName());
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
            String cityName = addresses.get(0).getAddressLine(0);
            Toast.makeText(WeatherActivity.this, cityName, Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {

    }
}
