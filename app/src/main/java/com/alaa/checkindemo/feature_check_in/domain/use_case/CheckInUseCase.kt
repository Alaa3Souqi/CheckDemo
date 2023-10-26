package com.alaa.checkindemo.feature_check_in.domain.use_case

import android.location.Location
import com.alaa.checkindemo.feature_auth.domain.model.CheckInState
import com.alaa.checkindemo.feature_auth.domain.repository.UserRepository
import com.alaa.checkindemo.feature_check_in.domain.repository.CheckInRepository
import com.alaa.checkindemo.util.Resource
import javax.inject.Inject

class CheckInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val checkInRepository: CheckInRepository
) {
    suspend operator fun invoke(
        currentDate: String,
        currentTime: String,
        currentLocation: Location
    ): Resource<Unit> =
        userRepository.getCurrentUser()?.let {

            if (it.checkIn.isEmpty() || it.checkIn.first().type != CheckInState.CheckedIn) {
                checkInRepository.checkIn(currentLocation, currentDate, currentTime, it.id)
            } else {
                Resource.Error("You are already checked in")
            }

        } ?: Resource.Error("Please login again")

}