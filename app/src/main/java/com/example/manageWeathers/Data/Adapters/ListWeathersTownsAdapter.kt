package com.example.manageWeathers.Data.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.manageWeathers.Data.Constantes
import com.example.manageWeathers.Data.db.WeatherTownEntity
import com.example.manageWeathers.R

/**
 * Present the adapter of the list of towns
 */
class ListWeathersTownsAdapter(var context: Context, var listWeatherTowns: ArrayList<WeatherTownEntity>): RecyclerView.Adapter<ListWeathersTownsAdapter.WeatherOfTownViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherOfTownViewHolder {
        val weatherOfTownItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_town, parent, false)

        return WeatherOfTownViewHolder(weatherOfTownItemView)
    }

    override fun onBindViewHolder(holder: WeatherOfTownViewHolder, position: Int) {
        val currentWeatherTown: WeatherTownEntity = listWeatherTowns[position]

        holder.townTextview?.text = "${currentWeatherTown.town}"
        holder.temperatureTextview?.text = "${context.getString(R.string.temperature_text)} ${Constantes.CONST_SEPERATOR} ${currentWeatherTown.temperature.toString()}"
        holder.pressureTextview?.text = "${context.getString(R.string.pressure_text)} ${Constantes.CONST_SEPERATOR} ${currentWeatherTown.pressure.toString()}"
        holder.humidityTextview?.text = "${context.getString(R.string.humidity_text)} ${Constantes.CONST_SEPERATOR} ${currentWeatherTown.humidity.toString()}"
        holder.seaLevelTextview?.text = "${context.getString(R.string.sealevel_text)} ${Constantes.CONST_SEPERATOR} ${currentWeatherTown.sea_level.toString()}"

    }

    override fun getItemCount(): Int {
        return listWeatherTowns.size
    }

    fun getWeatherOfTown(position: Int): WeatherTownEntity {
        return listWeatherTowns[position]
    }

    inner class WeatherOfTownViewHolder(itemView: View) : ViewHolder(itemView) {
        var townTextview: TextView? = itemView.findViewById(R.id.town_textview)
        var temperatureTextview: TextView? = itemView.findViewById(R.id.temperature_textview)
        var pressureTextview: TextView? = itemView.findViewById(R.id.pressure_textview)
        var humidityTextview: TextView? = itemView.findViewById(R.id.humidity_textview)
        var seaLevelTextview: TextView? = itemView.findViewById(R.id.sea_level_textview)
    }
}