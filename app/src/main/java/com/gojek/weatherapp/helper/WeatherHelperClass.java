package com.gojek.weatherapp.helper;

import android.support.annotation.NonNull;

import com.gojek.weatherapp.model.Forecastday;
import com.gojek.weatherapp.model.ModifiedWeatherResponse;
import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.utils.WeatherConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gojek.weatherapp.utils.WeatherUtility.getDayFromDate;

public class WeatherHelperClass {
    @NonNull
    public List<ModifiedWeatherResponse> processWeatherResponse(@NonNull WeatherResponse weatherResponse) {
        List<ModifiedWeatherResponse> modifiedWeatherResponseList = new ArrayList<>();
        Map<Integer, String> weekDaysMap = WeatherConstants.createDayMap();
        for (Forecastday forecastday : weatherResponse.getForecast().getForecastday()) {
            ModifiedWeatherResponse modifiedWeatherResponse = new ModifiedWeatherResponse();
            modifiedWeatherResponse.setDay(weekDaysMap.get(getDayFromDate(forecastday.getDate())));
            modifiedWeatherResponse.setTemperature(String.format("%s C", String.valueOf(Math.round(forecastday.getDay().getAvgtempC()))));
            modifiedWeatherResponseList.add(modifiedWeatherResponse);
        }
        return modifiedWeatherResponseList;
    }


}
