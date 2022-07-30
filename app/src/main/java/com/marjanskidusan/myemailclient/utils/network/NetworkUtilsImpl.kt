package com.marjanskidusan.myemailclient.utils.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkUtilsImpl @Inject constructor(private val context: Context): NetworkUtils {

    /*
    Checks whether the smartphone could theoretically establish an Internet connection.
    Whether an Internet connection actually exists, e.g. if the network quality is very poor,
    can only be determined with a ping or a web address.
     */
    override fun isInternetConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null
    }
}