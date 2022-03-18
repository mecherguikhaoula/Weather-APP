package com.example.manageWeathers.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.manageWeathers.Data.Constantes
import com.example.manageWeathers.R
import com.example.manageWeathers.databinding.ActivitySplashscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.jetbrains.anko.startActivity

class SplashScreenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        CoroutineScope(Dispatchers.Main).launch {
            updateProgressBar()
            startActivity<MainMenuActivity>()
            finish()
        }
    }

    /**
     * Initialize the components of the view
     */
    @SuppressLint("SetTextI18n")
    private fun initComponents() {
        splashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(splashscreenBinding.root)
        progressBar = splashscreenBinding.progressBarSplashScreen
        splashscreenBinding.libVersion.text =  "${getString(R.string.version_text)} ${Constantes.CONST_WEATHERS_TOWNS_VERSION}"
        // to pass on fullscreen
        supportActionBar?.hide()
    }

    /**
     *  Manage the progress of the progressBar
     */
    private suspend fun updateProgressBar() {
        var progress = 0

        CoroutineScope(Dispatchers.IO).async {
            while (progress < 125) {
                progress +=25
                delay(1000)
                progressBar.progress = progress
            }
        }.await()
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var splashscreenBinding: ActivitySplashscreenBinding
    private lateinit var progressBar: ProgressBar

}