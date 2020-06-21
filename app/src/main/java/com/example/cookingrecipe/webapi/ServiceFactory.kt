package com.example.cookingrecipe.webapi

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceFactory {

    companion object {
        fun <T> createService(context: Context,clazz: Class<T>?, endPoint: String?): T {
            val retrofit = Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client( getClient(context!!))
                .build()
            return retrofit.create(clazz)
        }

        private fun getClient(context: Context): OkHttpClient? {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build()
        }
    }
}