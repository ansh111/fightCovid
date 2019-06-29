package com.gojek.weatherapp.helper;

import com.gojek.weatherapp.model.Forecastday;
import com.gojek.weatherapp.model.ModifiedWeatherResponse;
import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.utils.WeatherConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gojek.weatherapp.utils.WeatherUtility.getDayFromDate;

public class WeatherHelperClass {
    public List<ModifiedWeatherResponse> processWeatherResponse(WeatherResponse weatherResponse) {
        List<ModifiedWeatherResponse> modifiedWeatherResponseList = new ArrayList<>();
        Map<Integer, String> weekDaysMap = WeatherConstants.createDayMap();
        for (Forecastday forecastday : weatherResponse.getForecast().getForecastday()) {
            ModifiedWeatherResponse modifiedWeatherResponse = new ModifiedWeatherResponse();
            modifiedWeatherResponse.setDay(weekDaysMap.get(getDayFromDate(forecastday.getDate())));
            modifiedWeatherResponse.setTemperature(String.valueOf(forecastday.getDay().getAvgtempC()));
            modifiedWeatherResponseList.add(modifiedWeatherResponse);
        }
        return modifiedWeatherResponseList;
    }


}
