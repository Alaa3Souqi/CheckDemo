package com.alaa.checkindemo.feature_check_in

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaa.checkindemo.feature_auth.domain.model.User
import com.alaa.checkindemo.feature_auth.domain.use_case.GetCurrentUser
import com.alaa.checkindemo.feature_check_in.domain.use_case.CheckInUseCase
import com.alaa.checkindemo.feature_check_in.domain.use_case.CheckOutUseCase
import com.alaa.checkindemo.feature_check_in.domain.use_case.GetCurrentDate
import com.alaa.checkindemo.feature_check_in.domain.use_case.GetCurrentTime
import com.alaa.checkindemo.util.Resource
import com.alaa.checkindemo.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val getCurrentDate: GetCurrentDate,
    private val getCurrentTime: GetCurrentTime,
    private val checkInUseCase: CheckInUseCase,
    private val getCurrentUser: GetCurrentUser,
    private val checkOutUseCase: CheckOutUseCase
) : ViewModel() {

    private val _checkInStatus = MutableLiveData<ScreenState<Unit>>()
    val checkInStatus: LiveData<ScreenState<Unit>> = _checkInStatus

    private val _checkOutStatus = MutableLiveData<ScreenState<Unit>>()
    val checkOutStatus: LiveData<ScreenState<Unit>> = _checkOutStatus


    private val _lastCheckInStatus = MutableLiveData<User?>()
    val lastCheckInStatus: LiveData<User?> = _lastCheckInStatus

    fun getUserLastState() {
        viewModelScope.launch(IO) {
            _lastCheckInStatus.postValue(getCurrentUser())
        }
    }

    fun checkIn(location: Location) {
        viewModelScope.launch(IO) {
            _checkInStatus.postValue(ScreenState.Loading())
            when (val result = checkInUseCase(getCurrentDate(), getCurrentTime(), location)) {
                is Resource.Error -> {
                    _checkInStatus.postValue(ScreenState.Error(result.message!!))
                }
                is Resource.Success -> {
                    _checkInStatus.postValue(ScreenState.Success(Unit))
                }
            }
        }
    }

    fun checkOut() {
        viewModelScope.launch(IO) {
            _checkInStatus.postValue(ScreenState.Loading())
            when (val result = checkOutUseCase(getCurrentDate(), getCurrentTime())) {
                is Resource.Error -> {
                    _checkOutStatus.postValue(ScreenState.Error(result.message!!))
                }
                is Resource.Success -> {
                    _checkOutStatus.postValue(ScreenState.Success(Unit))
                }
            }
        }
    }

}