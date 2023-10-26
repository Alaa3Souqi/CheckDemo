@file:JvmName("CheckInLogDtoKt")

package com.alaa.checkindemo.feature_auth.data.model

import com.alaa.checkindemo.feature_auth.domain.model.CheckInLog
import com.alaa.checkindemo.feature_auth.domain.model.CheckInState
import com.alaa.checkindemo.feature_auth.domain.model.Role
import com.alaa.checkindemo.feature_auth.domain.model.User

data class UserDto(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val lastState: String = "",
    val checkin: Map<String, CheckInLogDto> = emptyMap()
)

fun UserDto.toUser(): User {

    val role = if (role == "employee") Role.Employee else Role.Admin

    val checkInState = lastState.toCheckInState()

    val checkInLog = checkin.values
        .map { CheckInLog(it.date, it.time, it.type.toCheckInState()) }
        .sortedByDescending { it.date + it.time }

    return User(id, name, role, checkInState, checkInLog)
}

private fun String.toCheckInState() =
    if (this == "checkin") CheckInState.CheckedIn else CheckInState.CheckedOut

