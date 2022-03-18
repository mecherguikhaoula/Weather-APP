package com.example.manageWeathers.Repository

import androidx.lifecycle.LiveData
import com.example.manageWeathers.Data.Models.WeatherTownModel
import com.example.manageWeathers.Data.db.WeatherTownEntity
import com.example.manageWeathers.Network.ApiServicesImplementation
import javax.inject.Inject

/**
 * Class to bind between the data and the ViewModel
 */
class WeatherApplRepository@Inject constructor(private val apiService: ApiServicesImplementation) {

    // Get weather from the api
    suspend fun searchWeatherOfTown(town: String): WeatherTownModel {
        return apiService.searchWeatherOfTown(town)
    }

    suspend fun insert(weatherTown: WeatherTownEntity) {
        apiService.insert(weatherTown)
    }

    fun getAllFromRoom(): LiveData<List<WeatherTownEntity>> {
        return apiService.getAllFromRoom()
    }

    suspend fun deleteAll(){
        apiService.deleteAll()
    }
}