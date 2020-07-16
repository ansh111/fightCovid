package com.fight.covid.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fight.covid.rx.SchedulerProvider;

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


    @androidx.annotation.NonNull
    @NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull @NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FightCovidViewModel.class)) {
            return (T) new FightCovidViewModel(repository,schedulerProvider);
        }
        throw new IllegalArgumentException("Classname does not exist");
    }
}
