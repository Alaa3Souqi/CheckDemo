package com.alaa.checkindemo.feature_auth.domain.use_case

import com.alaa.checkindemo.feature_auth.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() =
        userRepository.getCurrentUser()
}