package com.gojek.weatherapp.model;

import com.gojek.weatherapp.utils.Status;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.gojek.weatherapp.utils.Status.ERROR;
import static com.gojek.weatherapp.utils.Status.LOADING;
import static com.gojek.weatherapp.utils.Status.SUCCESS;

public class ApiResponse {
    public final Status status;
    @Nullable
    public final WeatherResponse data;

    @Nullable
    public final Throwable error;

    public ApiResponse(Status status, WeatherResponse data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }
    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull WeatherResponse data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }
}
