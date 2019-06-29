package com.gojek.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModifiedWeatherResponse implements Parcelable {

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    String temperature;
    String day;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.temperature);
        dest.writeString(this.day);
    }

    public ModifiedWeatherResponse() {
    }

    protected ModifiedWeatherResponse(Parcel in) {
        this.temperature = in.readString();
        this.day = in.readString();
    }

    public static final Parcelable.Creator<ModifiedWeatherResponse> CREATOR = new Parcelable.Creator<ModifiedWeatherResponse>() {
        @Override
        public ModifiedWeatherResponse createFromParcel(Parcel source) {
            return new ModifiedWeatherResponse(source);
        }

        @Override
        public ModifiedWeatherResponse[] newArray(int size) {
            return new ModifiedWeatherResponse[size];
        }
    };
}
