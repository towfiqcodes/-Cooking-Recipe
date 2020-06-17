package com.example.cookingrecipe.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build

class InternetConnectionManager {
    companion object{

        fun isConnectedToInternet(context: Context): Boolean {
            val connManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = connManager.allNetworks
                var networkInfo: NetworkInfo
                for (mNetwork in networks) {
                    networkInfo = connManager.getNetworkInfo(mNetwork)
                    if (networkInfo.state != null) if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            } else {
                if (connManager != null) {
                    val info = connManager.allNetworkInfo
                    if (info != null) {
                        for (anInfo in info) {
                            if (anInfo.state != null) if (anInfo.state == NetworkInfo.State.CONNECTED) {
                                return true
                            }
                        }
                    }
                }
            }
            return false
        }

    }
}