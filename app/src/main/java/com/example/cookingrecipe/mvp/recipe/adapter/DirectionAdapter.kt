package com.example.cookingrecipe.mvp.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.R
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Direction
import kotlinx.android.synthetic.main.direction_list_item.view.*

class DirectionAdapter(val context: Context, val directionlist: List<Direction>) :
    RecyclerView.Adapter<DirectionAdapter.DirectionViewHolder>() {
    var direction: Direction? = null

    var animation: Animation? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectionViewHolder {
        var itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.direction_list_item, parent, false)
        return DirectionViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return directionlist.size
    }

    override fun onBindViewHolder(holder: DirectionViewHolder, position: Int) {
        direction = directionlist.get(position)

        animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_bottom)
        holder.itemView.startAnimation(animation)

        holder.tv_step.text = (" step ${direction!!.step.toString()}")
        holder.tvDirection.text = direction!!.text


        holder.btShowmore.setOnClickListener {
            if (holder.btShowmore.getText().toString().equals("read more...", ignoreCase = true)) {
                holder.tvDirection.setMaxLines(Integer.MAX_VALUE);
                holder.btShowmore.setText("read less");
            } else {
                holder.tvDirection.setMaxLines(2)
                holder.btShowmore.setText("read more...");
            }
        }


    }

    class DirectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_step = itemView.tv_step
        val tvDirection = itemView.tvDirection
        val btShowmore = itemView.btShowmore


    }
}