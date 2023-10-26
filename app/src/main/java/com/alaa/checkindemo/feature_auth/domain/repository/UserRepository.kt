package com.alaa.checkindemo.feature_auth.domain.repository

import com.alaa.checkindemo.feature_auth.domain.model.User

interface UserRepository {

    suspend fun getCurrentUser(): User?

}