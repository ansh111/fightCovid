package com.fight.covid.network;

import androidx.annotation.NonNull;

import com.fight.covid.model.Response;
import com.fight.covid.utils.FightCovidConstants;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCallInterface {


    @NonNull
    @GET(FightCovidConstants.API)
    Observable<Response> loadData();


}
