package com.example.manageWeathers.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manageWeathers.Data.db.WeatherTownEntity
import com.example.manageWeathers.Repository.WeatherApplRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTownsAddedViewModel@Inject constructor(private val weatherApplRepository: WeatherApplRepository): ViewModel() {

    fun getListTownsFromDatabase() {
      listTownsAdded = weatherApplRepository.getAllFromRoom()
    }

    @JvmName("getListTownsAdded1")
    fun getListTownsAdded(): LiveData<List<WeatherTownEntity>> {
        return this.listTownsAdded
    }

    fun insertTown(weatherTownCartModel: WeatherTownEntity) {
        viewModelScope.launch {
            weatherApplRepository.insert(weatherTownCartModel)
        }
    }

    fun deleteTowns() {
        viewModelScope.launch {
            weatherApplRepository.deleteAll()
        }
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var listTownsAdded: LiveData<List<WeatherTownEntity>> = MutableLiveData()
}