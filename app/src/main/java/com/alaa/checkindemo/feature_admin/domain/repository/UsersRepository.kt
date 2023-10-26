package com.alaa.checkindemo.feature_admin.domain.repository

import com.alaa.checkindemo.feature_auth.domain.model.User

interface UsersRepository {
    suspend fun getUsers(): List<User>?

    suspend fun getUserById(id: String): User?
}