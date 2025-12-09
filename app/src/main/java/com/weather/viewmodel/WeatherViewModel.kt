package com.weather.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.data.WeatherResponse
import com.weather.repository.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _weather = mutableStateOf<WeatherResponse?>(null)
    val weather: State<WeatherResponse?> = _weather

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _city = mutableStateOf("London")  // Default city
    val city: State<String> = _city

    private var refreshJob: Job? = null

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun fetchWeather() {
        viewModelScope.launch {
            try {
                _error.value = null
                val response = repository.getWeather(_city.value)
                _weather.value = response
                startPeriodicRefresh()
            } catch (e: Exception) {
                _error.value = "Error fetching weather: ${e.message}"
            }
        }
    }

    private fun startPeriodicRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (true) {
                delay(600000)  // 10 minutes
                fetchWeather()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }
}