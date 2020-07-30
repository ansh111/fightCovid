package com.fight.covid.ui

import androidx.lifecycle.LiveData
import com.fight.covid.FightCovidApplication
import com.fight.covid.model.Response
import com.fight.covid.network.ApiCallInterface
import com.fight.covid.room.Countries
import com.fight.covid.room.CountriesDao
import com.fight.covid.room.CountriesRoomDatabase
import io.reactivex.Observable
import javax.inject.Inject


class Repository @Inject constructor( var apiCallInterface: ApiCallInterface?,
var countryDao: CountriesDao?) {


    /*
     * method to call login api
     * */
    fun loadData(): Observable<Response> {
        return apiCallInterface!!.loadData()
    }
    suspend fun insert(country: Countries){
        countryDao?.insert(country)
    }

   suspend fun getCountryByCode(countryCode:String):Countries?{
        return countryDao?.getCountryByCode(countryCode)
    }


}