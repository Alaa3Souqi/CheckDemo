package com.alaa.checkindemo.di

import com.alaa.checkindemo.feature_admin.data.repository.UsersRepositoryImpl
import com.alaa.checkindemo.feature_admin.domain.repository.UsersRepository
import com.alaa.checkindemo.feature_auth.data.repository.LoginRepositoryImpl
import com.alaa.checkindemo.feature_auth.data.repository.UserRepositoryImpl
import com.alaa.checkindemo.feature_auth.domain.repository.LoginRepository
import com.alaa.checkindemo.feature_auth.domain.repository.UserRepository
import com.alaa.checkindemo.feature_check_in.data.repository.CheckInRepositoryImpl
import com.alaa.checkindemo.feature_check_in.data.repository.CheckOutRepositoryImpl
import com.alaa.checkindemo.feature_check_in.domain.repository.CheckInRepository
import com.alaa.checkindemo.feature_check_in.domain.repository.CheckOutRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideUserRepository(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): UserRepository =
        UserRepositoryImpl(firebaseAuth, firebaseDatabase)

    @Provides
    @Singleton
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth
    ): LoginRepository =
        LoginRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideUsersRepository(
        firebaseDatabase: FirebaseDatabase
    ): UsersRepository =
        UsersRepositoryImpl(firebaseDatabase)

    @Provides
    @Singleton
    fun provideCheckInRepository(
        firebaseDatabase: FirebaseDatabase
    ): CheckInRepository =
        CheckInRepositoryImpl(firebaseDatabase)

    @Provides
    @Singleton
    fun provideCheckOutRepository(
        firebaseDatabase: FirebaseDatabase
    ): CheckOutRepository =
        CheckOutRepositoryImpl(firebaseDatabase)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase =
        FirebaseDatabase.getInstance()

}