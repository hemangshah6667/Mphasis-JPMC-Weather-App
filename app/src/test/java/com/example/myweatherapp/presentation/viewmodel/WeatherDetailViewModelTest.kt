package com.example.myweatherapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myweatherapp.MainCoroutineScopeRule
import com.example.myweatherapp.domain.Location
import com.example.myweatherapp.domain.WeatherInfoModel
import com.example.myweatherapp.domain.usecase.GetGeoCodeUseCase
import com.example.myweatherapp.domain.usecase.GetWeatherInfoUseCase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


internal class WeatherDetailViewModelTest {

    lateinit var weatherDetailViewModel: WeatherDetailViewModel
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase = Mockito.mock()
    private val getGeoCodeUseCase: GetGeoCodeUseCase = Mockito.mock()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        weatherDetailViewModel = WeatherDetailViewModel(getWeatherInfoUseCase, getGeoCodeUseCase)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun verifyLoadDataSuccess() {
        runTest {
            Mockito.doReturn(
                WeatherInfoModel(
                    location = Location(10.90, 11.34),
                    cityName = "London",
                    weatherDescription = "rainy",
                    weatherID = 1,
                    weatherMain = "cloud",
                    iconID = "12d"
                )
            ).`when`(getWeatherInfoUseCase).invoke(Location(10.90, 11.34))

            weatherDetailViewModel.loadData(Location(10.90, 11.34))
            val dataValue = weatherDetailViewModel.weatherInfoLiveData.value
            Assert.assertEquals("London", dataValue?.cityName)
            //Here we can assert all other variables
        }


    }
}