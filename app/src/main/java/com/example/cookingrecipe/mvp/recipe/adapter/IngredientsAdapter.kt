package com.example.cookingrecipe.mvp.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.R
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Ingredient
import kotlinx.android.synthetic.main.ingredients_list_item.view.*

class IngredientsAdapter(val context: Context, var ingredientList:List<Ingredient>) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    var ingredient:Ingredient?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        var itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.ingredients_list_item, parent, false)
        return IngredientsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        ingredient=ingredientList.get(position)

        holder.tv_name.setText(ingredient!!.name)

        when(ingredient!!.name){
            "garlic"->{
                holder.iv_image.setImageResource(R.drawable.ic_garlic)

            }
            "olive oil"->{
                holder.iv_image.setImageResource(R.drawable.ic_olive_oil)

            }
            "kosher salt"->{
                holder.iv_image.setImageResource(R.drawable.ic_salt)

            }
            "black pepper"->{
                holder.iv_image.setImageResource(R.drawable.ic_pepper)

            }
            "thyme leaves"->{
                holder.iv_image.setImageResource(R.drawable.ic_leaves)

            }
            "Prime Rib Roast"->{
                holder.iv_image.setImageResource(R.drawable.ic_chicken)

            }
            else->{
                holder.iv_image.setImageResource(R.drawable.ic_favorite)
            }
        }

    }

    class IngredientsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val iv_image=itemView.iv_image

        val tv_name=itemView.tv_name

    }
}