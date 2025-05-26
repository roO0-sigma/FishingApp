package com.example.fishingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.fishingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(applicationContext)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if(sharedPreferencesManager.getUserLogStatus()) navController.navigate(R.id.homeFragment)
        else navController.navigate(R.id.registerFragment)
    }
}