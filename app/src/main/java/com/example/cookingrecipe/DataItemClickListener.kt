package com.example.cookingrecipe

import android.widget.LinearLayout
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Data
import de.hdodenhof.circleimageview.CircleImageView

interface DataItemClickListener {
    fun onDataClickListener(pos:Int, data: Data,  imageView: CircleImageView,  linearLayout: LinearLayout)
}