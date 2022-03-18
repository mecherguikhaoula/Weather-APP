package com.example.manageWeathers.Data.Models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * POJO class for the model Device for shop
 */
@Parcelize
data class WeatherTownModel(
    @SerializedName("temp")
    var temperature: Double = 0.0,
    @SerializedName("feelsLike")
    var feelsLike: Double = 0.0,
    @SerializedName("tempMinimum")
    var tempMinimum: Double = 0.0,
    @SerializedName("tempMax")
    var tempMax: Double= 0.0,
    @SerializedName("pressure")
    var pressure: Double = 0.0,
    @SerializedName("humidity")
    var humidity: Double = 0.0,
    @SerializedName("seaLevel")
    var seaLevel: Double = 0.0,
): Parcelable
