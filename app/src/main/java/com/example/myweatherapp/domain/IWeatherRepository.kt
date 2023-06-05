package com.example.myweatherapp.domain

import com.example.myweatherapp.data.WeatherInfoResponse
import com.example.myweatherapp.data.model.GeoCodeResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject

interface IWeatherRepository {
    suspend fun getWeatherDetails(location:Location): WeatherInfoResponse

    suspend fun getGeoCode(cityName:String): GeoCodeResponse
}