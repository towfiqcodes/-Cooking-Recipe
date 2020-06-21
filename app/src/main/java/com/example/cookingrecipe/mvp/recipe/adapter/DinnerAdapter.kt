package com.example.cookingrecipe.mvp.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.DataItemClickListener
import com.example.cookingrecipe.R
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Data
import kotlinx.android.synthetic.main.dinner_list_item.view.*

class DinnerAdapter(val context: Context, val dataList: List<Data>, val dataItemClickListener: DataItemClickListener) :
    RecyclerView.Adapter<DinnerAdapter.DinnerViewHolder>() {

    var data: Data? = null

    var animation:Animation?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DinnerViewHolder {
        var itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.dinner_list_item, parent, false)
        return DinnerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
      return dataList.size
    }

    override fun onBindViewHolder(holder: DinnerViewHolder, position: Int) {
        data =dataList.get(position)
        holder.recipe_name.text = data?.id
        holder.recipe_time.text = (data?.total_time).toString()+"cl"

        animation=AnimationUtils.loadAnimation(context,R.anim.slide_from_bottom)
        holder.itemView.startAnimation(animation)

        when(position){
            0->{
                holder.imageView.setImageResource(R.drawable.image3)
            }
        }

        holder.itemView.setOnClickListener{
            dataItemClickListener.onDataClickListener(holder.adapterPosition,data!!, holder.imageView,holder.ll_transition)
        }
    }


    class DinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView=itemView.civ_image
        val recipe_name=itemView.recipe_name
        val recipe_time=itemView.recipe_time
        val ll_transition=itemView.ll_transition

    }

}