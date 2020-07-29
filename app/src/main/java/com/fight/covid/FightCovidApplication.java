package com.fight.covid;

import android.app.Application;
import android.content.Context;

import com.fight.covid.dagger.AppComponent;
import com.fight.covid.dagger.AppModule;
import com.fight.covid.dagger.DaggerAppComponent;
import com.fight.covid.dagger.UtilsModule;

public class FightCovidApplication extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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
