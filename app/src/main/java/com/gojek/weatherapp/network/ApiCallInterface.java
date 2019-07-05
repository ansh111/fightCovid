package com.gojek.weatherapp.network;

import android.support.annotation.NonNull;

import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.utils.WeatherConstants;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @NonNull
    @POST(WeatherConstants.FORCAST_URL)
    Observable<WeatherResponse> loadForcast(@Query("key") String key, @Query("q") String city, @Query("days") int days);


}
