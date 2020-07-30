package com.fight.covid.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(country:Countries)

    @Query("DELETE FROM countries_table")
    suspend fun deleteAll()

    @Query("SELECT * from countries_table WHERE countryCode = :countryCode")
    suspend fun getCountryByCode(countryCode:String):Countries


}