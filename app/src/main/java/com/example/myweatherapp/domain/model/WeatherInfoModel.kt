package com.example.myweatherapp.domain.model

data class WeatherInfoModel(
    val location: Location,
    val weatherDescription: String,
    val cityName: String,
    val weatherID: Int,
    val weatherMain: String,
    val iconID: String
) {

    val weatherIconURL: String
        get() = "https://openweathermap.org/img/wn/${iconID}@2x.png"
}

data class Location(val lat: Double, val longitude: Double)
