package com.alaa.checkindemo.feature_check_in.data.repository

import android.location.Location
import com.alaa.checkindemo.feature_check_in.domain.repository.CheckInRepository
import com.alaa.checkindemo.util.Resource
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckInRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : CheckInRepository {

    override suspend fun checkIn(
        currentLocation: Location,
        currentDate: String,
        currentTime: String,
        userId: String
    ): Resource<Unit> {
        return try {

            if (currentLocation.distanceTo(getCompanyLocation()!!) < 3000) {
                val map = mapOf("time" to currentTime, "date" to currentDate, "type" to "checkin")

                firebaseDatabase.getReference("users")
                    .child(userId)
                    .child("lastState")
                    .setValue("checkin")

                val reference = firebaseDatabase.getReference("users")
                    .child(userId)
                    .child("checkin")
                    .push()

                reference.setValue(map)

                Resource.Success(Unit)

            } else
                Resource.Error("You aren't in the company")

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "error found")
        }
    }

    private suspend fun getCompanyLocation(): Location? {
        val mLatitude =
            firebaseDatabase.getReference("companyLocation").child("latitude").get().await()
                .getValue(Double::class.java)

        val mLongitude =
            firebaseDatabase.getReference("companyLocation").child("longitude").get().await()
                .getValue(Double::class.java)

        return if (mLatitude != null && mLongitude != null) {
            Location("dummy").apply {
                latitude = mLatitude
                longitude = mLongitude
            }
        } else
            null
    }
}