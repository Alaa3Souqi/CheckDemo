package com.alaa.checkindemo.feature_auth.data.repository

import com.alaa.checkindemo.feature_auth.data.model.UserDto
import com.alaa.checkindemo.feature_auth.data.model.toUser
import com.alaa.checkindemo.feature_auth.domain.model.User
import com.alaa.checkindemo.feature_auth.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : UserRepository {

    override suspend fun getCurrentUser(): User? =
        getCurrentUserId()?.let { uid ->
            getUserById(uid)
        }

    private suspend fun getUserById(userId: String): User? {
        val user = firebaseDatabase.getReference("users").child(userId)

        val userDto = user.get().await().getValue(UserDto::class.java)

        return userDto?.toUser()
    }

    private fun getCurrentUserId(): String? =
        firebaseAuth.currentUser?.uid
}