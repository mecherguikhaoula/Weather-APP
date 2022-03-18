package com.example.manageWeathers.Data.Models

import com.google.gson.annotations.SerializedName

data class ResponseCallAPiModel(
    @SerializedName("main")
    var weatherInformations: WeatherTownModel,
    @SerializedName("message")
    var errorMessage: String = ""

)