package com.example.manageWeathers.Data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.manageWeathers.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: WeatherOfTownDao

    @Before
    override public fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        dao = database.getWeatherOfTownDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun selectWeathersOfTownsFromDBTest() = runBlockingTest {
        val weatherToAdd1 = WeatherTownEntity(1,"Tunisia", 30.5, 40.5, 41.8, 80.14)
        val weatherToAdd2 = WeatherTownEntity(2,"London", 30.5, 40.5, 41.8, 14.5)
        val weatherToAdd3 = WeatherTownEntity(3,"Egypt", 30.5, 40.5, 41.8, 58.25)

        val dataAdded = listOf(weatherToAdd1, weatherToAdd2, weatherToAdd3)

        dao.insertweatherOfTown(weatherToAdd1)
        dao.insertweatherOfTown(weatherToAdd2)
        dao.insertweatherOfTown(weatherToAdd3)

        val selectWeathersOfTowns = dao.getAll().getOrAwaitValue()

        assertThat(selectWeathersOfTowns).isEqualTo(dataAdded)
    }

    @Test
    fun insertTownToDBTest() = runBlockingTest {
        val weatherToAdd1 = WeatherTownEntity(1,"Tunisia", 30.5, 40.5, 41.8, 80.14)

        dao.insertweatherOfTown(weatherToAdd1)
        val selectTowns = dao.getAll().getOrAwaitValue()

        assertThat(selectTowns).contains(weatherToAdd1)
    }
}