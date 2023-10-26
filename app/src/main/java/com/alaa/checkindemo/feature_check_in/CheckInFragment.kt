package com.alaa.checkindemo.feature_check_in

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alaa.checkindemo.R
import com.alaa.checkindemo.databinding.FragmentCheckInBinding
import com.alaa.checkindemo.feature_auth.domain.model.CheckInState
import com.alaa.checkindemo.util.ScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckInFragment : Fragment(R.layout.fragment_check_in) {

    companion object {
        const val REQUEST_CODE = 101
    }

    private lateinit var locationByGps: Location
    private lateinit var locationByNetwork: Location

    private var _binding: FragmentCheckInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CheckInViewModel by viewModels()

    lateinit var locationManager: LocationManager

    private val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByGps = location
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val networkLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByNetwork = location
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckInBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserLastState()
        viewModel.lastCheckInStatus.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.progressBar.isVisible = false
                if (user.checkIn.isNotEmpty() && user.checkIn.first().type == CheckInState.CheckedIn) {
                    binding.groupCheckOut.isVisible = true
                    binding.groupCheckIn.isVisible = false
                } else {
                    binding.groupCheckIn.isVisible = true
                    binding.groupCheckOut.isVisible = false
                }
            }
        }

        viewModel.checkInStatus.observe(viewLifecycleOwner) { state ->
            setUpCheckInScreenState(state)
        }

        viewModel.checkOutStatus.observe(viewLifecycleOwner) { state ->
            setUpCheckOutScreenState(state)
        }

        binding.ivCheckIn.setOnClickListener {
            getCurrentLocation()?.let {
                viewModel.checkIn(it)
            }

        }

        binding.ivCheckOut.setOnClickListener {
            viewModel.checkOut()
        }

        binding.ivSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(CheckInFragmentDirections.actionCheckInFragmentToLoginFragment())
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Location? {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isLocationPermissionGranted()) {

            if (hasGps) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener
                )
            }

            if (hasNetwork) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener
                )
            }

            val lastKnownLocationByNetwork =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            lastKnownLocationByNetwork?.let {
                locationByNetwork = lastKnownLocationByNetwork
            }

            val lastKnownLocationByGps =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocationByGps?.let {
                locationByGps = lastKnownLocationByGps
            }

            return if (locationByGps.accuracy > locationByNetwork.accuracy) {
                locationByGps
            } else {
                locationByNetwork
            }
        }
        return null
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
            false
        } else {
            true
        }
    }

    private fun setUpCheckInScreenState(state: ScreenState<Unit>) {
        binding.apply {
            when (state) {
                is ScreenState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    progressBar.isVisible = false
                    groupCheckIn.isVisible = true
                    groupCheckOut.isVisible = false
                }
                is ScreenState.Loading -> {
                    groupCheckIn.isVisible = false
                    groupCheckOut.isVisible = false
                    progressBar.isVisible = true
                }
                is ScreenState.Success -> {
                    groupCheckIn.isVisible = false
                    groupCheckOut.isVisible = true
                    progressBar.isVisible = false
                }
            }
        }
    }

    private fun setUpCheckOutScreenState(state: ScreenState<Unit>) {
        binding.apply {
            when (state) {
                is ScreenState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    progressBar.isVisible = false
                    groupCheckIn.isVisible = false
                    groupCheckOut.isVisible = true
                }
                is ScreenState.Loading -> {
                    groupCheckIn.isVisible = false
                    groupCheckOut.isVisible = false
                    progressBar.isVisible = true
                }
                is ScreenState.Success -> {
                    groupCheckIn.isVisible = true
                    groupCheckOut.isVisible = false
                    progressBar.isVisible = false
                }
            }
        }
    }
}