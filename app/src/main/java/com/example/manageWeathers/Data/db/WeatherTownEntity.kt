package com.example.manageWeathers.Data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "townsTable")
@Parcelize
data class WeatherTownEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "town") val town: String?,
    @ColumnInfo(name = "temperature") val temperature: Double?,
    @ColumnInfo(name = "pressure") val pressure: Double?,
    @ColumnInfo(name = "humidity") val humidity: Double?,
    @ColumnInfo(name = "seaLevel") val sea_level: Double?
): Parcelable
