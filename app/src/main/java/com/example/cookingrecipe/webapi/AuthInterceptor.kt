package com.example.cookingrecipe.webapi

import android.content.Context
import com.example.cookingrecipe.utility.SharedPrefsHandler
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sharedPrefsHandler = SharedPrefsHandler(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sharedPrefsHandler.getUserToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}