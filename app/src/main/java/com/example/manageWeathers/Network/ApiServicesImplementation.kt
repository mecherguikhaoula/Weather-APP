package com.example.manageWeathers.Network

import androidx.lifecycle.LiveData
import com.example.manageWeathers.Data.Models.WeatherTownModel
import com.example.manageWeathers.Data.db.WeatherOfTownDao
import com.example.manageWeathers.Data.db.WeatherTownEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class ApiServicesImplementation@Inject constructor(private val townsDao: WeatherOfTownDao, private val weathersTownsApi: RetrofitService) {
    // Get weather from the api
    suspend fun searchWeatherOfTown(town: String): WeatherTownModel {
        var weatherOfTown:  WeatherTownModel? = null

        CoroutineScope(Dispatchers.IO).async {
            val response = weathersTownsApi.getWeatherOfTown(town)

            weatherOfTown = if (response.body()!!.weatherInformations != null) {
                response.body()!!.weatherInformations
            } else {
            null
        }
        }.await()
        return  weatherOfTown!!
    }

    suspend fun insert(weatherTown: WeatherTownEntity) {
        townsDao.insertweatherOfTown(weatherTown)
    }

    fun getAllFromRoom(): LiveData<List<WeatherTownEntity>> {
        return townsDao.getAll()
    }

    suspend fun deleteAll(){
        townsDao.deleteAll()
    }
}