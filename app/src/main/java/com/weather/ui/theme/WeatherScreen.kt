package com.weather.ui.theme



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weather by viewModel.weather
    val error by viewModel.error
    val city by viewModel.city

    var inputCity by remember { mutableStateOf(city) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = inputCity,
            onValueChange = {
                inputCity = it
            },
            label = { Text("Enter City") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.updateCity(inputCity)
            viewModel.fetchWeather()
        }) {
            Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (error != null) {
            Text(text = error!!, color = MaterialTheme.colorScheme.error)
        } else if (weather != null) {
            val temp = weather!!.main.temp
            val desc = weather!!.weather.firstOrNull()?.description ?: "N/A"
            val humidity = weather!!.main.humidity
            val cityName = weather!!.cityName

            Text(text = "City: $cityName")
            Text(text = "Temperature: $temp °C")
            Text(text = "Description: $desc")
            Text(text = "Humidity: $humidity%")
        } else {
            Text(text = "Enter a city and fetch weather")
        }
    }
}