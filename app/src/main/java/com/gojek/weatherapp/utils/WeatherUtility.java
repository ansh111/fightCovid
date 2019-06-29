package com.gojek.weatherapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherUtility {
    public static int getDayFromDate(String dateString) {
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (c.get(Calendar.DAY_OF_WEEK));
    }
}
