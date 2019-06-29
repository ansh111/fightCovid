package com.gojek.weatherapp.helper;

import android.text.format.DateFormat;

import com.gojek.weatherapp.model.Forecastday;
import com.gojek.weatherapp.model.ModifiedWeatherResponse;
import com.gojek.weatherapp.model.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

public class WeatherHelperClass {
    public List<ModifiedWeatherResponse> processWeatherResponse(WeatherResponse weatherResponse) {
        List<ModifiedWeatherResponse> modifiedWeatherResponseList = new ArrayList<>();
        for (Forecastday forecastday : weatherResponse.getForecast().getForecastday()) {
            ModifiedWeatherResponse modifiedWeatherResponse = new ModifiedWeatherResponse();
            modifiedWeatherResponse.setDay(String.valueOf(DateFormat.format("EEEE", forecastday.getDateEpoch())));
            modifiedWeatherResponse.setTemperature(String.valueOf(forecastday.getDay().getAvgtempC()));
            modifiedWeatherResponseList.add(modifiedWeatherResponse);
        }
        return modifiedWeatherResponseList;
    }
}
