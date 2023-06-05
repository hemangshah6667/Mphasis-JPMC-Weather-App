package com.example.myweatherapp.data.repository

import com.example.myweatherapp.data.WeatherInfoResponse
import com.example.myweatherapp.data.WeatherService
import com.example.myweatherapp.data.model.GeoCodeResponse
import com.example.myweatherapp.domain.IWeatherRepository
import com.example.myweatherapp.domain.Location
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) :
    IWeatherRepository {
    override suspend fun getWeatherDetails(location: Location): WeatherInfoResponse {
        return weatherService.getWeatherInfo(location.lat.toString(), location.longitude.toString())
    }

    override suspend fun getGeoCode(cityName: String): GeoCodeResponse {
        return weatherService.getGeoCode(cityName.replace(" ", ","))
    }
}