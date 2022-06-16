package com.yunushamod.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment == null){
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commitNow()
        }
    }

    override fun onCrimeSelected(uuid: UUID) {
        val crimeFragment = CrimeFragment.newInstance(uuid)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, crimeFragment)
            .addToBackStack(null)
            .commit()
    }
    companion object{
        private const val TAG = "MainActivity"
    }
}