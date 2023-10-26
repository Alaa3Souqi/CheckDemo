package com.alaa.checkindemo.feature_auth.domain.use_case

import com.alaa.checkindemo.feature_auth.domain.model.Role
import com.alaa.checkindemo.feature_auth.domain.repository.UserRepository
import com.alaa.checkindemo.util.UserAuthDestination
import javax.inject.Inject

class UserAuthDestinationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserAuthDestination {
        return userRepository.getCurrentUser()?.let { user ->
            when (user.role) {
                Role.Admin -> UserAuthDestination.AdminDashboard
                Role.Employee -> UserAuthDestination.Employee
            }
        } ?: UserAuthDestination.Login
    }
}