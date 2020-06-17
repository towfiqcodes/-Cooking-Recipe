package com.example.cookingrecipe.mvp.recipe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingrecipe.R
import com.example.cookingrecipe.mvp.login.LoginActivity
import com.example.cookingrecipe.utility.SharedPrefsHandler

class RecipeActivity : AppCompatActivity() {
    var activity: RecipeActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        activity = this
    }


    private fun logout() {
        SharedPrefsHandler.clear()
        try {
            Thread.sleep(16)
        } catch (x: Exception) {
            //do nothing
        }
        val loginIntent = Intent(activity, LoginActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(loginIntent)
        activity!!.finish()
    }
}
