package com.example.myweatherapp.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import com.example.myweatherapp.data.repository.WeatherRepository
import com.example.myweatherapp.domain.model.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetGeoCodeUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    @ApplicationContext appContext: Context,
) {
    //given more time we can implement shared preference read and write in
    // different module shared preference manager and inject in fragment as well as usecase
    private val sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences("Location", 0)

    suspend operator fun invoke(cityName: String): Location {
        val geoCodeResponse = weatherRepository.getGeoCode(cityName)
        sharedPreferences.edit()
            .putFloat("lat", geoCodeResponse.first().lat.toFloat())
            .putFloat("long", geoCodeResponse.first().lon.toFloat())
            .apply()
        //here i am picking first data from the response result
        //but given more time or based on UI requirement we can give option to user to select city based on the response and then display result
        return Location(geoCodeResponse.first().lat, geoCodeResponse.first().lon)
    }
}