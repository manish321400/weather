package com.weather.data



import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("name") val cityName: String
)

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double
)

data class Weather(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)