package com.gojek.weatherapp.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.gojek.weatherapp.model.ApiResponse;
import com.gojek.weatherapp.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private final SchedulerProvider schedulerProvider;


    public  WeatherViewModel(Repository repository ,SchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }
    @NonNull
    public MutableLiveData<ApiResponse> forcastResponse() {
        return responseLiveData;
    }


    public void loadForcast(String key, String city, int days) {
        disposables.add(repository.loadForcast(key, city, days).
                subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe((obj) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        response -> responseLiveData.setValue(ApiResponse.success(response)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))

                )
        );
    }
    @Override
    protected void onCleared() {
       disposables.clear();
    }
}
