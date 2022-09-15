package com.yunushamod.android.criminalintent

import android.app.Application
import com.yunushamod.android.criminalintent.services.CrimeRepository

class CriminalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}