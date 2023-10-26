package com.alaa.checkindemo.feature_auth.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaa.checkindemo.feature_auth.domain.repository.LoginRepository
import com.alaa.checkindemo.feature_auth.domain.use_case.UserAuthDestinationUseCase
import com.alaa.checkindemo.util.Resource
import com.alaa.checkindemo.util.ScreenState
import com.alaa.checkindemo.util.UserAuthDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userAuthDestinationUseCase: UserAuthDestinationUseCase
) : ViewModel() {

    private val _userLoginStatus = MutableLiveData<ScreenState<UserAuthDestination>>()
    val userLoginStatus: LiveData<ScreenState<UserAuthDestination>> = _userLoginStatus

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _userLoginStatus.postValue(ScreenState.Loading())

            when (val result = loginRepository.login(email, password)) {
                is Resource.Error ->
                    _userLoginStatus.postValue(ScreenState.Error(result.message!!))
                is Resource.Success ->
                    _userLoginStatus.postValue(ScreenState.Success(userAuthDestinationUseCase()))
            }
        }
    }
}