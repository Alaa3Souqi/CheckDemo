package com.alaa.checkindemo.feature_check_in.domain.use_case

import com.alaa.checkindemo.feature_auth.domain.model.CheckInState
import com.alaa.checkindemo.feature_auth.domain.repository.UserRepository
import com.alaa.checkindemo.feature_check_in.domain.repository.CheckOutRepository
import com.alaa.checkindemo.util.Resource
import javax.inject.Inject

class CheckOutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val checkOutRepository: CheckOutRepository
) {
    suspend operator fun invoke(
        currentDate: String,
        currentTime: String
    ): Resource<Unit> =
        userRepository.getCurrentUser()?.let {

            if (it.checkIn.isNotEmpty() && it.checkIn.first().type != CheckInState.CheckedOut) {
                checkOutRepository.checkOut(currentDate, currentTime, it.id)
            } else {
                Resource.Error("You are already checked in")
            }

        } ?: Resource.Error("Please login again")

}
