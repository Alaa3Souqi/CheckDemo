package com.alaa.checkindemo.feature_admin.data.repository

import com.alaa.checkindemo.feature_admin.data.model.UsersDto
import com.alaa.checkindemo.feature_admin.data.model.toUsers
import com.alaa.checkindemo.feature_admin.domain.repository.UsersRepository
import com.alaa.checkindemo.feature_auth.data.model.toUser
import com.alaa.checkindemo.feature_auth.domain.model.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
) : UsersRepository {

    override suspend fun getUsers(): List<User>? {
        val usersDto =
            firebaseDatabase.reference.get().await().getValue(UsersDto::class.java)

        return usersDto?.toUsers()
    }

    override suspend fun getUserById(id: String): User? {
        val usersDto =
            firebaseDatabase.reference.get().await().getValue(UsersDto::class.java)

        return usersDto?.users?.get(id)?.toUser()
    }
}