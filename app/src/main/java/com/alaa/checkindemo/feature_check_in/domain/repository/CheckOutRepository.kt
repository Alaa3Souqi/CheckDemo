package com.alaa.checkindemo.feature_check_in.domain.repository

import com.alaa.checkindemo.util.Resource

interface CheckOutRepository {
    suspend fun checkOut(currentDate: String, currentTime: String, userId: String): Resource<Unit>
}