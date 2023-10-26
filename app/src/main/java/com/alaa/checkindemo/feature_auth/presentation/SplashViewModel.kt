package com.alaa.checkindemo.feature_auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaa.checkindemo.feature_auth.domain.use_case.UserAuthDestinationUseCase
import com.alaa.checkindemo.util.UserAuthDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userAuthDestination: UserAuthDestinationUseCase
) : ViewModel() {

    private val _navigationPath = MutableLiveData<UserAuthDestination>()
    val navigationPath: LiveData<UserAuthDestination>
        get() = _navigationPath

    fun getCurrentUser() {
        viewModelScope.launch {
            _navigationPath.postValue((userAuthDestination()))
        }
    }
}