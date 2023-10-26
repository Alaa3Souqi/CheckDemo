package com.alaa.checkindemo.app

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
//        FirebaseDatabase.getInstance().setPersistenceEnabled(false)
    }
}