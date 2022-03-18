package com.example.manageWeathers.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.manageWeathers.Data.Constantes.Companion.CONST_SEPERATOR
import com.example.manageWeathers.Data.Models.WeatherTownModel
import com.example.manageWeathers.Data.Utils.Companion.alertDialog
import com.example.manageWeathers.Data.Utils.Companion.displayAlert
import com.example.manageWeathers.Data.db.WeatherTownEntity
import com.example.manageWeathers.R
import com.example.manageWeathers.ViewModels.ListTownsAddedViewModel
import com.example.manageWeathers.ViewModels.SearchWeatherOfTownViewModel
import com.example.manageWeathers.databinding.ActivitySearchWeatherTownBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWeatherOfTownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view
     */
    private fun initComponents() {
        searchWeatherTownBinding = ActivitySearchWeatherTownBinding.inflate(layoutInflater)
        setContentView(searchWeatherTownBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.search_weather_title_text)
        searchWeatherOfTownViewModel = ViewModelProvider(this).get(SearchWeatherOfTownViewModel::class.java)
        handleDatabaseViewModel = ViewModelProvider(this).get(ListTownsAddedViewModel::class.java)
        searchWeatherTextInputLayout = searchWeatherTownBinding.searchWeatherTownTextInputLayout
        searchWeatherTextInputLayout.requestFocus()
        searchWeatherEditext = searchWeatherTownBinding.searchWeatherTownTextInputEditText
        temperatureMaterialTextView = searchWeatherTownBinding.temperatureTextview
        feelsLikeMaterialTextView = searchWeatherTownBinding.feelsLikeTextview
        townMaterialTextView = searchWeatherTownBinding.townTextview
        tempMinMaterialTextView = searchWeatherTownBinding.tempMinTextview
        tempMaxMaterialTextView = searchWeatherTownBinding.tempMaxTextview
        pressureMaterialTextView = searchWeatherTownBinding.pressureTextview
        humidityMaterialTextView = searchWeatherTownBinding.humidityTextview
        seaLevelMaterialTextView = searchWeatherTownBinding.seaLevelTextview
        searchWeatherBottom = searchWeatherTownBinding.addCartCancelBottomnavigationview
    }

    /**
     * Manage the listeners of the class SearchWeatherOfTownActivity
     */
    private fun setEvent() {

        // Observe the progress Bar
        searchWeatherOfTownViewModel.getIsProgressing().observe(this, {
            if (searchWeatherOfTownViewModel.isProgressingBar.value!!) {
                searchWeatherTownBinding.progressBar.isVisible = true
                searchWeatherTownBinding.loadingDataMaterialTextView.isVisible = true
            } else {
                searchWeatherTownBinding.progressBar.visibility = View.GONE
                searchWeatherTownBinding.loadingDataMaterialTextView.visibility = View.GONE
            }
        })

        // Handle the click on the button search
        searchWeatherTextInputLayout.setEndIconOnClickListener {
            if (searchWeatherEditext.text.isEmpty()) {
                searchWeatherEditext.error = getString(R.string.add_town_text)
            } else {
                searchWeatherOfTownViewModel.searchWeatherFromApi(searchWeatherEditext.text.toString())
            }
        }

        searchWeatherOfTownViewModel.getListWeathersOfTowns().observe(this, {
            if (searchWeatherOfTownViewModel.weatherOfTown.value != null) {
                weatherOfTown = searchWeatherOfTownViewModel.weatherOfTown.value!!
                displayWeatherInformations(weatherOfTown)

            } else {
                displayAlert(
                    this,
                    null,
                    { finish() },
                    getString(R.string.call_api_error_message),
                    getString(R.string.ok_text),
                    null
                )
            }
        })
        searchWeatherBottom.setOnItemSelectedListener { item -> addActionBottomViewClick(item.itemId) }

    }

    /**
     * Display the informations of the weather of a town
     */
    @SuppressLint("SetTextI18n")
    fun displayWeatherInformations(weatherOfTown: WeatherTownModel) {
        townMaterialTextView.text = "${getString(R.string.weather_text)} ${searchWeatherEditext.text}"
        temperatureMaterialTextView.text = "${getString(R.string.temperature_text)} $CONST_SEPERATOR ${weatherOfTown.temperature}"
        feelsLikeMaterialTextView.text = "${getString(R.string.feels_like_text)} $CONST_SEPERATOR ${weatherOfTown.feelsLike}"
        tempMinMaterialTextView.text = "${getString(R.string.temp_min_text)} $CONST_SEPERATOR ${weatherOfTown.tempMinimum}"
        tempMaxMaterialTextView.text = "${getString(R.string.temp_max_text)} $CONST_SEPERATOR ${weatherOfTown.tempMax}"
        pressureMaterialTextView.text = "${getString(R.string.pressure_text)} $CONST_SEPERATOR ${weatherOfTown.pressure}"
        humidityMaterialTextView.text = "${getString(R.string.humidity_text)} $CONST_SEPERATOR ${weatherOfTown.humidity}"
        seaLevelMaterialTextView.text = "${getString(R.string.sealevel_text)} $CONST_SEPERATOR ${weatherOfTown.seaLevel}"
        searchWeatherBottom.visibility = View.VISIBLE
    }

    /**
     * Add the action of click on the differents menus of BottomNavigationView
     */
    private fun addActionBottomViewClick(item: Int): Boolean {
        when (item) {
            R.id.add_button -> { 
                displayAlert(
                    this,
                    null,
                    ::addTown,
                    getString(R.string.confirm_add_town_text),
                    getString(R.string.yes_text),
                    getString(R.string.no_text)
                )
            }

            R.id.cancel_button -> {
                finish()
            }
        }
        return true
    }

    private fun addTown() {
        handleDatabaseViewModel.insertTown(
            WeatherTownEntity(0,
                searchWeatherEditext.text.toString(),
                weatherOfTown.temperature,
                weatherOfTown.pressure,
                weatherOfTown.humidity,
                weatherOfTown.seaLevel
            )
        )
        alertDialog.dismiss()
        Toast.makeText(this, getString(R.string.town_added_text), Toast.LENGTH_SHORT).show()
    }

    /**
     * Manage the click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var searchWeatherTownBinding: ActivitySearchWeatherTownBinding
    private lateinit var searchWeatherOfTownViewModel: SearchWeatherOfTownViewModel
    private lateinit var searchWeatherTextInputLayout: TextInputLayout
    private lateinit var searchWeatherEditext: EditText
    private lateinit var weatherOfTown: WeatherTownModel
    private lateinit var feelsLikeMaterialTextView: MaterialTextView
    private lateinit var townMaterialTextView: MaterialTextView
    private lateinit var temperatureMaterialTextView: MaterialTextView
    private lateinit var tempMinMaterialTextView: MaterialTextView
    private lateinit var tempMaxMaterialTextView: MaterialTextView
    private lateinit var pressureMaterialTextView: MaterialTextView
    private lateinit var humidityMaterialTextView: MaterialTextView
    private lateinit var seaLevelMaterialTextView: MaterialTextView
    private lateinit var searchWeatherBottom: BottomNavigationView
    private lateinit var handleDatabaseViewModel: ListTownsAddedViewModel
}