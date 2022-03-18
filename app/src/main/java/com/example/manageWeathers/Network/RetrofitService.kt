package com.example.manageWeathers.Network

import com.example.manageWeathers.Data.Models.ResponseCallAPiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("weather")
    suspend fun getWeatherOfTown(@Query("q") param1: String,
                                 @Query("appid") param2: String = "f1c9b47bf5a912182ffaf1251fb533cd"): Response<ResponseCallAPiModel>
}