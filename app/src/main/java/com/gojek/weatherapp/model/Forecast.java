
package com.gojek.weatherapp.model;

import android.support.annotation.Nullable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast {

    @Nullable
    @SerializedName("forecastday")
    @Expose
    private List<Forecastday> forecastday = null;

    @Nullable
    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }

}
