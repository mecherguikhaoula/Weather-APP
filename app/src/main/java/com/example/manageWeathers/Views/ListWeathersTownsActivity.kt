package com.example.manageWeathers.Views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manageWeathers.Data.Adapters.ListWeathersTownsAdapter
import com.example.manageWeathers.Data.Utils
import com.example.manageWeathers.Data.db.WeatherTownEntity
import com.example.manageWeathers.R
import com.example.manageWeathers.ViewModels.ListTownsAddedViewModel
import com.example.manageWeathers.databinding.ActivityListWeatherTownsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListWeathersTownsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponenets()
        setEvent()
    }

    /**
     * Initialize the components of the view MainMenuActivity
     */
    private fun initComponenets() {
        binding = ActivityListWeatherTownsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listTownsViewModel = ViewModelProvider(this).get(ListTownsAddedViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.list_weathers_towns_text)
        listWeathersTowns = intent.extras?.get("listTowns") as ArrayList<WeatherTownEntity>
        listOfWeathersRecyclerView = binding.listWeathersListview
        listOfWeathersRecyclerView.layoutManager = LinearLayoutManager(this)
        listTownsAdapter = ListWeathersTownsAdapter(this, listWeathersTowns)
        listOfWeathersRecyclerView.adapter = listTownsAdapter
    }

    /**
     * Manage the click on the listeners of the class MainMenuActivity
     */
    private fun setEvent() {
        binding.deleteCancelBottomNavigationView.setOnItemSelectedListener { item -> addActionBottomViewClick(item.itemId) }
    }

    /**
     * Add the action of click on the differents menus of BottomNavigationView
     */
    private fun addActionBottomViewClick(item: Int): Boolean {
        when (item) {
            R.id.delete_button -> {
                Utils.displayAlert(
                    this,
                    null,
                    ::confirmCartAction,
                    getString(R.string.confirm_delete_towns_text),
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

    /**
     * Manage the click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Handle the confirm of a cart
     */
    private fun confirmCartAction() {
        listTownsViewModel.deleteTowns()
        finish()
        Toast.makeText(this, getString(R.string.towns_deleted_text), Toast.LENGTH_SHORT).show()
     }


    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------


    private lateinit var binding: ActivityListWeatherTownsBinding
    private lateinit var listOfWeathersRecyclerView: RecyclerView
    private lateinit var listWeathersTowns: ArrayList<WeatherTownEntity>
    private lateinit var listTownsViewModel: ListTownsAddedViewModel
    private lateinit var listTownsAdapter: ListWeathersTownsAdapter

}