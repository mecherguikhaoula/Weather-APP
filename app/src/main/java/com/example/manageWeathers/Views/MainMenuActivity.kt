package com.example.manageWeathers.Views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.manageWeathers.Data.Utils.Companion.displayAlert
import com.example.manageWeathers.Data.db.WeatherTownEntity
import com.example.manageWeathers.R
import com.example.manageWeathers.ViewModels.ListTownsAddedViewModel
import com.example.manageWeathers.databinding.ActivityMainmenuBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.startActivity

@AndroidEntryPoint
class MainMenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view MainMenuActivity
     */
    private fun initComponents() {
        mainmenuBinding = ActivityMainmenuBinding.inflate(layoutInflater)
        setContentView(mainmenuBinding.root)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainmenuViewModel = ViewModelProvider(this).get(ListTownsAddedViewModel::class.java)
    }

    /**
     * Manage the click on the listeners of the class MainMenuActivity
     */
    private fun setEvent() {
        mainmenuBinding.collectInformationWeatherButton.setOnClickListener {
            startActivity<SearchWeatherOfTownActivity>()
        }

        mainmenuBinding.listTownsAddedButton.setOnClickListener {
            mainmenuViewModel.getListTownsFromDatabase()

            mainmenuViewModel.getListTownsAdded().observe(this, {
                val listTownsAdded = mainmenuViewModel.listTownsAdded.value!!

                if (!listTownsAdded.isNullOrEmpty()) {
                    val intent = Intent(this@MainMenuActivity, ListWeathersTownsActivity::class.java)

                    intent.putExtra(
                        "listTowns",
                        mainmenuViewModel.listTownsAdded.value as ArrayList<WeatherTownEntity>
                    )
                    startActivity(intent)
                } else {
                    displayAlert(
                        this@MainMenuActivity,
                        null,
                        { },
                        getString(R.string.no_town_added),
                        getString(R.string.ok_text),
                        null
                    )
                }
            })
        }
    }

    /**
     * Manage the click on the back button
     */
    override fun onSupportNavigateUp(): Boolean {
        displayAlert(this, null, {finishAffinity()}, getString(R.string.quit_app_text), getString(R.string.yes_text), getString(R.string.no_text))
        return true
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onPause() {
        mainmenuViewModel.getListTownsAdded().removeObservers(this)
        super.onPause()
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var mainmenuBinding: ActivityMainmenuBinding
    private lateinit var mainmenuViewModel: ListTownsAddedViewModel
}