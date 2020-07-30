package com.fight.covid.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Countries::class),version = 1,exportSchema = false)
public abstract class CountriesRoomDatabase : RoomDatabase(){
    abstract fun countriesDao():CountriesDao

    private class CountriesRoomCallback(
            private val scope:CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database-> scope.launch {
                var countryDao = database.countriesDao()
                countryDao.deleteAll()
            }}
        }
    }
    companion object{
        @Volatile
        private var INSTANCE:CountriesRoomDatabase?=null
        fun getDatabase(context: Context) : CountriesRoomDatabase{
            val temp = INSTANCE
            if(temp != null){
                return temp
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CountriesRoomDatabase::class.java,
                        "countries_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }

}