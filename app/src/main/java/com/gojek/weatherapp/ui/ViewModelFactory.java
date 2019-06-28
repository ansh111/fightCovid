package com.gojek.weatherapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            return (T) new WeatherViewModel(repository);
        }
        throw new IllegalArgumentException("Classname does not exist");
    }
}
