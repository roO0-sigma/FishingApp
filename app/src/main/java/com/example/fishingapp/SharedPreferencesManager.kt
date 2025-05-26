package com.example.fishingapp

import android.content.Context

class SharedPreferencesManager(context: Context) {
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun forgetUser() {
        sharedPreferences.edit().putBoolean("userLogStatus", false).apply()
    }

    fun userLogIn() {
        sharedPreferences.edit().putBoolean("userLogStatus", true).apply()
    }

    fun getUserLogStatus() : Boolean {
        return sharedPreferences.getBoolean("userLogStatus", false)
    }
}