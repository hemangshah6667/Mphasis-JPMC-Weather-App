package com.example.myweatherapp.domain.usecase

import com.example.myweatherapp.data.repository.WeatherRepository
import com.example.myweatherapp.domain.model.Location
import com.example.myweatherapp.domain.model.WeatherInfoModel

import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(location: Location): WeatherInfoModel {
        val weatherInfoResponse = weatherRepository.getWeatherDetails(location)
        return WeatherInfoModel(location = Location(weatherInfoResponse.coord.lat,weatherInfoResponse.coord.lon),
            cityName = weatherInfoResponse.name,
            weatherDescription = weatherInfoResponse.weather.first().description,
        weatherID = weatherInfoResponse.weather.first().id,
        weatherMain = weatherInfoResponse.weather.first().main,
        iconID = weatherInfoResponse.weather.first().icon)
    }

}