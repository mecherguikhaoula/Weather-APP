package com.example.manageWeathers.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manageWeathers.Data.Models.WeatherTownModel
import com.example.manageWeathers.Repository.WeatherApplRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchWeatherOfTownViewModel@Inject constructor(private val weatherApplRepository: WeatherApplRepository): ViewModel() {
    fun getListWeathersOfTowns(): LiveData<WeatherTownModel> {
        return weatherOfTown
    }

    fun searchWeatherFromApi(town: String) {
        var responseCallApi: WeatherTownModel?

        isProgressingBar.value = true
        viewModelScope.launch(Main) {
            try {
                responseCallApi = weatherApplRepository.searchWeatherOfTown(town)
                weatherOfTown.postValue(responseCallApi)
            } catch (e: Exception) {
                weatherOfTown.postValue(null)
            }
            isProgressingBar.postValue(false)
        }
    }

    fun getIsProgressing(): LiveData<Boolean> {
        return isProgressingBar
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var weatherOfTown = MutableLiveData<WeatherTownModel>()
    var isProgressingBar: MutableLiveData<Boolean> = MutableLiveData(false)
}