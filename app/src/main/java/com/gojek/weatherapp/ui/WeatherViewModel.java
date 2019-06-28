package com.gojek.weatherapp.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.gojek.weatherapp.model.ApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

class WeatherViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public  WeatherViewModel(Repository repository) {
        this.repository = repository;
    }
    public MutableLiveData<ApiResponse> forcastResponse() {
        return responseLiveData;
    }


    public void loadForcast(String key, String city, int days) {
        disposables.add(repository.loadForcast(key, city, days).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
