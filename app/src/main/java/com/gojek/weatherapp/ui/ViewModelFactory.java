package com.gojek.weatherapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.gojek.weatherapp.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Repository repository;
    private SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelFactory(Repository repository,SchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }


    @android.support.annotation.NonNull
    @NonNull
    @Override
    public <T extends ViewModel> T create(@android.support.annotation.NonNull @NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            return (T) new WeatherViewModel(repository,schedulerProvider);
        }
        throw new IllegalArgumentException("Classname does not exist");
    }
}
