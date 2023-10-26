package com.alaa.checkindemo.feature_check_in.data.repository

import com.alaa.checkindemo.feature_check_in.domain.repository.CheckOutRepository
import com.alaa.checkindemo.util.Resource
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class CheckOutRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : CheckOutRepository {
    override suspend fun checkOut(
        currentDate: String,
        currentTime: String,
        userId: String
    ): Resource<Unit> {
        return try {
            val map = mapOf("time" to currentTime, "date" to currentDate, "type" to "checkout")

            firebaseDatabase.getReference("users")
                .child(userId)
                .child("lastState")
                .setValue("checkedOut")

            val reference = firebaseDatabase.getReference("users")
                .child(userId)
                .child("checkin")
                .push()

            reference.setValue(map)

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "error found")
        }
    }
}