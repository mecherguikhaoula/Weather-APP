package com.example.manageWeathers.Data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Update

@Dao
interface WeatherOfTownDao {
    @Query("SELECT * FROM townsTable")
    fun getAll(): LiveData<List<WeatherTownEntity>>

    @Query("DELETE FROM townsTable")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(user: WeatherTownEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertweatherOfTown(weatherTown: WeatherTownEntity)

    @Update
    suspend fun update(weatherTown: WeatherTownEntity)
}