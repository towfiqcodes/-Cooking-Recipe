package com.example.cookingrecipe.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingrecipe.R


class RecipeActivity : AppCompatActivity() {
    var activity: RecipeActivity? = null
    var image1:ImageView?=null
    var image2:ImageView?=null
    var image3:ImageView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        activity = this

        image1= findViewById<ImageView>(R.id.imageView1)
        image2= findViewById<ImageView>(R.id.imageView2)
        image3= findViewById<ImageView>(R.id.imageView3)







    }


    /*private fun logout() {
        shar
        try {
            Thread.sleep(16)
        } catch (x: Exception) {
            //do nothing
        }
        val loginIntent = Intent(activity, LoginActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(loginIntent)
        activity!!.finish()
    }*/


}
