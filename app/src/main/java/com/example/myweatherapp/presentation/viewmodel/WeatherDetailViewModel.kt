package com.example.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.domain.Location
import com.example.myweatherapp.domain.WeatherInfoModel
import com.example.myweatherapp.domain.usecase.GetGeoCodeUseCase
import com.example.myweatherapp.domain.usecase.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(private val useCase: GetWeatherInfoUseCase, private val geoCodeUseCase: GetGeoCodeUseCase) :
    ViewModel() {
    val weatherInfoLiveData = MutableLiveData<WeatherInfoModel>()
    val weatherErrorLiveData = MutableLiveData<Boolean>(false)

     fun loadData(location: Location) {
        viewModelScope.launch {
            try {
                weatherInfoLiveData.postValue(useCase.invoke(location))
                weatherErrorLiveData.postValue(false)
            } catch (e: Exception) {
                println(e)
                weatherErrorLiveData.postValue(true)
            }

        }
    }


    fun searchCity(cityName:String) {
        viewModelScope.launch {
            //instead of try catch here i would prefer to use sealed class for different response like Success, Progress, Error etc
            try {
                //currently not handling empty search result UI on screen
               loadData(geoCodeUseCase.invoke(cityName))
            } catch (e: Exception) {

            }

        }
    }
}