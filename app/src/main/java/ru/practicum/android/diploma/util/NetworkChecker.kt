package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkChecker(private val context: Context) {
    fun isNetworkAvailable(): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
