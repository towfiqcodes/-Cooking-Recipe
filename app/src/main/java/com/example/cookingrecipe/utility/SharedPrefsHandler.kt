package com.example.cookingrecipe.utility

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPrefsHandler{

    private var sharedPrefs:SharedPreferences?=null


    constructor(context: Context?){
    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
}


    fun clear() {
        val editor = sharedPrefs!!.edit()
        editor.clear()
        editor.apply()
    }



    fun getUserToken(): String? {
        return sharedPrefs!!.getString("mobile_token", null)
    }

    fun setUserToken(mobileToken: String?) {
        val editor = sharedPrefs!!.edit()
        editor.putString("mobile_token", mobileToken)
        editor.apply()
    }
}


