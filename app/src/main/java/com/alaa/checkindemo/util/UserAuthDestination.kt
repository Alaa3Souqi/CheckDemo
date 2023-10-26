package com.alaa.checkindemo.util

sealed class UserAuthDestination {
    object Login : UserAuthDestination()
    object Employee : UserAuthDestination()
    object AdminDashboard : UserAuthDestination()
}