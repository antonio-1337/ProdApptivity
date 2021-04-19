package com.example.testapplication.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import utils.NoInternetException


class NetworkConnectionInterceptor(
        context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable())
            throw NoInternetException("Make you your device has an active Internet connection")
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return connectivityManager.activeNetworkInfo != null

        } else {

            for (network in connectivityManager.allNetworks) { // added in API 21 (Lollipop)
                val networkCapabilities: NetworkCapabilities? =
                        connectivityManager.getNetworkCapabilities(network)
                return (networkCapabilities!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                        (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
            }
        }
        return false
    }

}