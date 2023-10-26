package com.alaa.checkindemo.feature_auth.data.repository

import com.alaa.checkindemo.feature_auth.domain.repository.LoginRepository
import com.alaa.checkindemo.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {

    override suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error found")
        }

    }
}