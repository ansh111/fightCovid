package com.fight.covid.ui;

import com.fight.covid.model.Response;
import com.fight.covid.network.ApiCallInterface;

import io.reactivex.Observable;

public class Repository {
    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call login api
     * */
    public Observable<Response> loadData() {
        return apiCallInterface.loadData();
    }
}
