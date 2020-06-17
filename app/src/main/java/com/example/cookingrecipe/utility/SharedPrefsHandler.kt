package com.example.cookingrecipe.utility

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.cookingrecipe.AppApplication

class SharedPrefsHandler{

constructor(context: Context?){
    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
}
    private var sharedPrefs:SharedPreferences?=null


    companion object{
        const val keyuseremail = "email"
        const val keyuserpassword = "password"
        const val keyusertoken = "token"
        const val keyModelTestPrefs = "modelTestPrefs"


        private fun getSharedPreferences(): SharedPreferences? {
            return AppApplication.instance
                ?.getSharedPreferences(keyModelTestPrefs, 0)
        }


        fun clear() {
            //  SharedPreferences.Editor editor = sharedPrefs.edit();
            val pref = getSharedPreferences()
            val editor = pref!!.edit()
            editor.clear()
            editor.apply()
        }
    }





    fun setUserToken(token:String){
        val preferences= getSharedPreferences()
        val editor=preferences!!.edit()

        editor.putString(keyusertoken,token)
        editor.apply()
    }


    fun getUserToken(): String? {
        val pref = getSharedPreferences()
        return pref!!.getString(keyusertoken, null)
    }
}


