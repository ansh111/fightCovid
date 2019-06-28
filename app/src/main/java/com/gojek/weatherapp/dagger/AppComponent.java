package com.gojek.weatherapp.dagger;

import com.gojek.weatherapp.ui.WeatherActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(WeatherActivity weatherActivity);

}
