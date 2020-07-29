package com.fight.covid.dagger;

import android.content.Context;

import androidx.annotation.NonNull;

import com.fight.covid.FightCovidApplication;
import com.fight.covid.room.CountriesDao;
import com.fight.covid.room.CountriesRoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @NonNull
    @Provides
    CountriesDao getCountriesDao(){
        return  CountriesRoomDatabase.Companion.getDatabase(context).countriesDao();
    }
}
