package com.fight.covid.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fight.covid.model.Timeseries

@Entity(tableName="countries_table")
data class Countries(@PrimaryKey @ColumnInfo(name = "countryCode") val countryCode: String,
                     val name:String,
                     val flag:String, val reports: Int, val cases:Int, val deaths:Int, val recovered:Int) {
}