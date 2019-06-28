package com.gojek.weatherapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gojek.weatherapp.R;
import com.gojek.weatherapp.WeatherApplication;
import com.gojek.weatherapp.model.ApiResponse;

import javax.inject.Inject;

public class WeatherActivity extends AppCompatActivity {


    @Inject
    ViewModelFactory viewModelFactory;
    WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((WeatherApplication) getApplication()).getAppComponent().doInjection(this);
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        weatherViewModel.forcastResponse().observe(this, this::consumeResponse);
        weatherViewModel.loadForcast("69e792543e624e7cb11180545192806", "Gurgaon", 4);


    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Toast.makeText(WeatherActivity.this, getResources().getString(R.string.loading), Toast.LENGTH_SHORT).show();
                break;

            case SUCCESS:
                Toast.makeText(WeatherActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                break;

            case ERROR:
                Toast.makeText(WeatherActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
