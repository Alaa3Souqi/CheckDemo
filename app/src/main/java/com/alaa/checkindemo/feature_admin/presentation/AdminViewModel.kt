package com.alaa.checkindemo.feature_admin.presentation

import androidx.lifecycle.*
import com.alaa.checkindemo.feature_admin.domain.repository.UsersRepository
import com.alaa.checkindemo.feature_auth.domain.model.User
import com.alaa.checkindemo.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _usersLiveData = MutableLiveData<ScreenState<List<User>>>()
    val usersLiveData: LiveData<ScreenState<List<User>>> = _usersLiveData

    private val _userById = MutableLiveData<ScreenState<User>>()
    val userById: LiveData<ScreenState<User>> = _userById

    fun getUsers() {
        viewModelScope.launch(IO) {
            _usersLiveData.postValue(ScreenState.Loading())
            usersRepository.getUsers()?.let {
                _usersLiveData.postValue(ScreenState.Success(it))
            } ?: _usersLiveData.postValue(ScreenState.Error(""))

        }
    }

    fun getUserByCheckInLogById() {
        val userId = savedStateHandle.get<String>("userId")

        userId?.let {

            viewModelScope.launch(IO) {
                val data = usersRepository.getUserById(userId)

                _userById.postValue(ScreenState.Loading())

                if (data != null) {
                    _userById.postValue(ScreenState.Success(data))
                } else
                    _userById.postValue(ScreenState.Error("wow"))

            }
        }


    }
}