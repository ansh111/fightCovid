package com.gojek.weatherapp;

import android.app.AppComponentFactory;
import android.app.Application;
import android.content.Context;

import com.gojek.weatherapp.dagger.AppComponent;
import com.gojek.weatherapp.dagger.AppModule;
import com.gojek.weatherapp.dagger.DaggerAppComponent;
import com.gojek.weatherapp.dagger.UtilsModule;

public class WeatherApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
