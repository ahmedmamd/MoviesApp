package com.example.social.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.social.R
import com.example.social.databinding.ItemCastCrewBinding
import com.example.social.pojo.castAndCrew.CastAndCrew

class CastAdapter(
    var cast: ArrayList<CastAndCrew>,
    val context: Context
): RecyclerView.Adapter<CastAdapter.CastViewHolder>() {


    class CastViewHolder(val binding: ItemCastCrewBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding : ItemCastCrewBinding
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cast_crew,parent,false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.binding.cast = cast.get(position)
        holder.itemView.animation =  AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation)
    }

    override fun getItemCount(): Int {
        return cast.size
    }
}