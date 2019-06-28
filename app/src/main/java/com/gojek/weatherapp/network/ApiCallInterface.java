package com.gojek.weatherapp.network;

import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.utils.WeatherConstants;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCallInterface {
    @FormUrlEncoded
    @POST(WeatherConstants.FORCAST_URL)
    Observable<WeatherResponse> loadForcast(@Query("key") String key, @Field("q") String city, @Field("days") int days);


}
