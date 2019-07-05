package com.gojek.weatherapp;

import android.arch.core.executor.ArchTaskExecutor;
import android.arch.core.executor.TaskExecutor;
import android.support.annotation.NonNull;

import com.gojek.weatherapp.model.WeatherResponse;
import com.gojek.weatherapp.network.ApiCallInterface;
import com.gojek.weatherapp.ui.Repository;
import com.gojek.weatherapp.ui.WeatherViewModel;
import com.google.gson.Gson;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherViewModelTest {
    WeatherViewModel viewModel;
    @Mock
    Repository repository;
    Observable<WeatherResponse> weatherResponseObservable;
    String response;
    private TestScheduler mTestScheduler;
    WeatherResponse weatherResponse;
    ApiCallInterface apiCallInterface;

    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        Gson g = new Gson();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        viewModel = new WeatherViewModel(repository, testSchedulerProvider);
        response = new String(Files.readAllBytes(Paths.get("response.txt")));
        WeatherResponse weatherResponse = g.fromJson(response, WeatherResponse.class);
        weatherResponseObservable = Observable.just(weatherResponse);

    }

    @BeforeClass
    public static void setupSchedulers() throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(Runnable runnable) {
                runnable.run();
            }

            @Override
            public void postToMainThread(@NonNull Runnable runnable) {
                runnable.run();
            }

            @Override
            public boolean isMainThread() {
                return true;
            }

        });
    }

    @Test
    public void weatherAPIPositiveTest() {
        when(repository.loadForcast(anyString(), anyString(), anyInt())).thenReturn(weatherResponseObservable);
        viewModel.loadForcast("API_KEY", "gurgaon", 4);
        mTestScheduler.triggerActions();
        Assert.assertNotNull(viewModel.forcastResponse().getValue().data);
        Assert.assertNotNull(viewModel.forcastResponse().getValue().data.getForecast());
        Assert.assertNotNull(viewModel.forcastResponse().getValue().data.getForecast().getForecastday());
        Assert.assertNotNull("3",viewModel.forcastResponse().getValue().data.getForecast().getForecastday().size());

    }

    @Test(expected = NullPointerException.class)
    public void weatherAPINegativeTest() {
        when(repository.loadForcast(anyString(), anyString(), anyInt())).thenReturn(null);
        viewModel.loadForcast("API_KEY", "gurgaon", 4);
        mTestScheduler.triggerActions();
        Assert.assertNotNull(viewModel.forcastResponse());

    }

    @AfterClass
    public static void removeSchedulers() {
        ArchTaskExecutor.getInstance().setDelegate(null);
    }


}
