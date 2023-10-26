package com.alaa.checkindemo.feature_auth.domain.repository

import com.alaa.checkindemo.util.Resource

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<Unit>
}