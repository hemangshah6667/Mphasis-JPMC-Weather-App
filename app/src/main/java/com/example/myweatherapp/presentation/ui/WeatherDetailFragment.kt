package com.example.myweatherapp.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myweatherapp.databinding.FragmentFirstBinding
import com.example.myweatherapp.domain.model.Location
import com.example.myweatherapp.presentation.viewmodel.WeatherDetailViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView. private val viewModel by viewModels<SearchViewModel>()
    private val binding get() = _binding!!
    private val viewModel by viewModels<WeatherDetailViewModel>()
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissions ->
        when (permissions) {
            true -> {
                // Only approximate location access granted.
                getCurrentLocationForWeatherData()
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }

            else -> {
                // we can implement No location access flow here.
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //given more time we can implement shared preference read and write in
        // different module shared preference manager and inject in fragment as well as use case
        context?.let {
            val sharedPreferences: SharedPreferences =
                it.applicationContext.getSharedPreferences("Location", 0)
            if (sharedPreferences.contains("lat")) {
                val lat = sharedPreferences.getFloat("lat", 0f).toDouble()
                val long = sharedPreferences.getFloat("long", 0f).toDouble()
                viewModel.loadData(Location(lat, long))
            } else {
                if (ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocationForWeatherData()
                } else {
                    locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }

            }
        }
        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner) {
            binding.tvWeatherDetails.text = it.cityName + " - " + it.weatherDescription
            //i have used Glide library here to manage image related operations
            Glide.with(this).load(it.weatherIconURL).into(binding.iconWeather);
        }
        binding.buttonFirst.setOnClickListener {
            viewModel.searchCity(binding.searchText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocationForWeatherData() {
        fusedLocationClient?.lastLocation?.addOnSuccessListener {
            viewModel.loadData(Location(it.latitude, it.longitude))
        }
    }

}