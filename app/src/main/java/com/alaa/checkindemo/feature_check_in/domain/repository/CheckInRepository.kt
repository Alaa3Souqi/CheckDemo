package com.alaa.checkindemo.feature_check_in.domain.repository

import android.location.Location
import com.alaa.checkindemo.util.Resource

interface CheckInRepository {
    suspend fun checkIn(
        currentLocation: Location,
        currentDate: String,
        currentTime: String,
        userId: String
    ): Resource<Unit>
}

