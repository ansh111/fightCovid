package com.fight.covid.dagger

import android.content.Context
import com.fight.covid.room.CountriesDao
import com.fight.covid.room.CountriesRoomDatabase.Companion.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {


    @Provides
    @Singleton
    fun providesCountriesDao(@ApplicationContext appContext: Context):CountriesDao{
        return getDatabase(appContext).countriesDao()
    }


}