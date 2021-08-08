package com.example.social.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.R
import com.example.social.databinding.ItemLatestAdapterBinding
import com.example.social.databinding.ItemVedioBinding
import com.example.social.pojo.vedio.Result_vedio

class VedioAdapter(
    var vedio: ArrayList<Result_vedio>,
    val context: Context
): RecyclerView.Adapter<VedioAdapter.VedioViewHolder>() {

    class VedioViewHolder(val binding: ItemVedioBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VedioViewHolder {
        val binding : ItemVedioBinding
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_vedio,parent,false)
        return VedioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VedioViewHolder, position: Int) {
        holder.binding.vedio = vedio.get(position)
        holder.itemView.animation =  AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.google.android.youtube")
            intent.data = Uri.parse("https://www.youtube.com/watch?v="+vedio[position].key)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return vedio.size
    }
}