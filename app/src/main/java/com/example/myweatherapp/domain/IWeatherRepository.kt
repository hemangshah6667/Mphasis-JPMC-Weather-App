package com.example.myweatherapp.domain

import com.example.myweatherapp.data.model.WeatherInfoResponse
import com.example.myweatherapp.data.model.GeoCodeResponse
import com.example.myweatherapp.domain.model.Location

interface IWeatherRepository {
    suspend fun getWeatherDetails(location: Location): WeatherInfoResponse

    suspend fun getGeoCode(cityName:String): GeoCodeResponse
}