package com.yunushamod.android.criminalintent.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(), var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false, var suspect: String = ""
){
    val photoFileName: String
    get() = "IMG_$id"
}