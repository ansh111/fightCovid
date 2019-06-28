package com.gojek.weatherapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gojek.weatherapp.R;

public class WeatherActivity extends AppCompatActivity {



    ViewModelFactory viewModelFactory;
    WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
