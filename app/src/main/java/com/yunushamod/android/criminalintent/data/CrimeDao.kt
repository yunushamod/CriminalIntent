package com.yunushamod.android.criminalintent.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yunushamod.android.criminalintent.models.Crime
import java.util.*

@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime WHERE id = (:id)")
    fun getCrime(id: UUID): LiveData<Crime?>
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>
    @Update
    fun updateCrime(crime: Crime)
    @Insert
    fun addCrime(crime: Crime)
}