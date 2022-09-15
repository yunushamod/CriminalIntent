package com.yunushamod.android.criminalintent.viewmodels

import androidx.lifecycle.ViewModel
import com.yunushamod.android.criminalintent.models.Crime
import com.yunushamod.android.criminalintent.services.CrimeRepository

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
    fun addCrime(crime: Crime) = crimeRepository.addCrime(crime)
}