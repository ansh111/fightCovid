package com.fight.covid.model;

import com.fight.covid.utils.Status;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.fight.covid.utils.Status.ERROR;
import static com.fight.covid.utils.Status.LOADING;
import static com.fight.covid.utils.Status.SUCCESS;

public class ApiResponse {
    public final Status status;
    @Nullable
    public final Response data;

    @Nullable
    public final Throwable error;

    public ApiResponse(Status status, Response data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }
    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull Response data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }
}
