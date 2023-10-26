package com.alaa.checkindemo.feature_auth.domain.model

data class User(
    val id: String,
    val name: String,
    val role: Role,
    val lastState: CheckInState,
    val checkIn: List<CheckInLog>
)

