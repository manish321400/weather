package com.weather.repository

import kotlin.jvm.java



import com.weather.data.WeatherResponse
import com.weather.network.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val apiKey = "YOUR_API_KEY_HERE"  // Replace with your OpenWeatherMap API key

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: WeatherService = retrofit.create(WeatherService::class.java)

    suspend fun getWeather(city: String): WeatherResponse {
        return service.getWeather(city, apiKey)
    }
}