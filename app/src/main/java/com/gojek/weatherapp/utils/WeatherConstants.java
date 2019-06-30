package com.gojek.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Map;

public class WeatherConstants {
    public static final String BASE_URL = "http://api.apixu.com/v1/";
    public static final String FORCAST_URL = "forecast.json";
    public static final int PERMISSION_REQUEST_LOCATION = 1 ;
    public static final String API_KEY = "69e792543e624e7cb11180545192806";

    public static Map<Integer, String> createDayMap() {
        Map<Integer, String> weekDays = new HashMap<>();
        weekDays.put(0, "Sunday");
        weekDays.put(1, "Monday");
        weekDays.put(2, "Tuesday");
        weekDays.put(3, "Wednesday");
        weekDays.put(4, "Thursday");
        weekDays.put(5, "Friday");
        weekDays.put(6, "Saturday");
        return weekDays;
    }


}
