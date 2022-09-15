package com.yunushamod.android.criminalintent.services

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.yunushamod.android.criminalintent.data.CrimeDatabase
import com.yunushamod.android.criminalintent.data.migration_1_2
import com.yunushamod.android.criminalintent.models.Crime
import java.io.File
import java.util.*
import java.util.concurrent.Executors

class CrimeRepository private constructor(context: Context) {
    private val database: CrimeDatabase = Room.databaseBuilder(context.applicationContext,
    CrimeDatabase::class.java, DATABASE_NAME).addMigrations(migration_1_2).build()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir
    fun getCrime(id: UUID): LiveData<Crime?> = database.crimeDao().getCrime(id)
    fun getCrimes(): LiveData<List<Crime>> = database.crimeDao().getCrimes()
    fun addCrime(crime: Crime){
        executor.execute{
            database.crimeDao().addCrime(crime)
        }
    }

    fun updateCrime(crime: Crime){
        executor.execute{
            database.crimeDao().updateCrime(crime)
        }
    }

    fun getPhotoFile(crime: Crime) = File(filesDir, crime.photoFileName)
    companion object{
        private const val DATABASE_NAME = "crime-database"
        private var INSTANCE: CrimeRepository? = null
        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }
        fun get(): CrimeRepository = INSTANCE ?: throw IllegalStateException("Crime Repository must be initialized")
    }
}