package com.alaa.checkindemo.feature_admin.data.model

import com.alaa.checkindemo.feature_auth.data.model.UserDto
import com.alaa.checkindemo.feature_auth.data.model.toUser
import com.alaa.checkindemo.feature_auth.domain.model.User

data class UsersDto(val users: Map<String, UserDto> = emptyMap())

fun UsersDto.toUsers(): List<User> =
    users.values.map { it.toUser() }