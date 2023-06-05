package com.example.myweatherapp.data.model

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
//https://api.openweathermap.org/
    @GET("data/2.5/weather")
    suspend fun getWeatherInfo(@Query("lat") lat: String, @Query("lon") lon:String, @Query("appid") appKey:String = "b2704a5df3ba0b80a30b35249e094cbd"): WeatherInfoResponse

    //http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    @GET("geo/1.0/direct")
    suspend fun getGeoCode(@Query("q",encoded = true) cityName:String, @Query("limit") limit:Int = 5, @Query("appid") appKey:String = "b2704a5df3ba0b80a30b35249e094cbd"): GeoCodeResponse
}