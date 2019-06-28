package com.gojek.weatherapp.ui;

import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.network.ApiCallInterface;

import io.reactivex.Observable;

public class Repository {
    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call login api
     * */
    public Observable<WeatherResponse> loadForcast(String key ,String city, int days) {
        return apiCallInterface.loadForcast(key,city, days);
    }
}
