package com.example.cookingrecipe

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

    companion object {

        @get:Synchronized
        var instance: AppApplication? = null
            private set

        /*public void setConnectivityListener(NetworkChangeReceiver.ConnectivityReceiverListener listener) {
        NetworkChangeReceiver.connectivityReceiverListener =listener;
    }*/
        var context: Context? = null
            private set

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}