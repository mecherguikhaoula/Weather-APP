package com.example.manageWeathers.DI

import android.content.Context
import com.example.manageWeathers.Data.db.AppDatabase
import com.example.manageWeathers.Data.db.WeatherOfTownDao
import com.example.manageWeathers.Network.ApiServicesImplementation
import com.example.manageWeathers.Network.RetrofitBuilder
import com.example.manageWeathers.Network.RetrofitService
import com.example.manageWeathers.Repository.WeatherApplRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRepository(weatherOfTownDao: WeatherOfTownDao, retrofitService: RetrofitService): ApiServicesImplementation {
        return ApiServicesImplementation(weatherOfTownDao, retrofitService)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitBuilder().createRetrofitInstance()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): RetrofitService{
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideServiceA(apiService: ApiServicesImplementation): WeatherApplRepository{
        return WeatherApplRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WeatherOfTownDao {
        return AppDatabase.getDatabase(context).getWeatherOfTownDao()
    }
}