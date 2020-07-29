package com.fight.covid.dagger;

import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.fight.covid.FightCovidApplication;
import com.fight.covid.network.ApiCallInterface;
import com.fight.covid.room.CountriesDao;
import com.fight.covid.room.CountriesRoomDatabase;
import com.fight.covid.rx.AppSchedulerProvider;
import com.fight.covid.rx.SchedulerProvider;
import com.fight.covid.ui.Repository;
import com.fight.covid.ui.ViewModelFactory;
import com.fight.covid.utils.FightCovidConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @NonNull
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @NonNull
    @Provides
    @Singleton
    Retrofit provideRetrofit(@NonNull Gson gson, @NonNull OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(FightCovidConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }



    @NonNull
    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .build();
            return chain.proceed(request);
        })
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @NonNull
    @Provides
    @Singleton
    Repository getRepository(ApiCallInterface apiCallInterface, CountriesDao countriesDao) {

        return new Repository(apiCallInterface,countriesDao);
    }

    @NonNull
    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository myRepository, SchedulerProvider schedulerProvider) {
        return new ViewModelFactory(myRepository, schedulerProvider);
    }

    @NonNull
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


}
