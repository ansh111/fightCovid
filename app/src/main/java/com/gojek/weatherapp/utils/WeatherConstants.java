package com.gojek.weatherapp.utils;

import java.util.HashMap;
import java.util.Map;

public class WeatherConstants {
    public static final String BASE_URL = "http://api.apixu.com/v1/";
    public static final String FORCAST_URL = "forecast.json";

    public static Map<Integer, String> createDayMap() {
        Map<Integer, String> weekDays = new HashMap<>();
        weekDays.put(1, "Sunday");
        weekDays.put(2, "Monday");
        weekDays.put(3, "Tuesday");
        weekDays.put(4, "Wednesday");
        weekDays.put(5, "Thursday");
        weekDays.put(6, "Friday");
        weekDays.put(7, "Saturday");
        return weekDays;
    }


}
