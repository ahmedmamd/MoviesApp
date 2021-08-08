package com.example.social.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.R
import com.example.social.databinding.ItemLatestAdapterBinding
import com.example.social.ui.TopRated
import com.example.social.viewModell.HomeViewModel

class LatestMovieAdapter(
    var movie: ArrayList<ResultItem>,
    val context: Context
) : RecyclerView.Adapter<LatestMovieAdapter.LatestViewHolder>() {
    var homeViewModel: HomeViewModel? = HomeViewModel()

    class LatestViewHolder(val binding: ItemLatestAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestViewHolder {
        val binding: ItemLatestAdapterBinding
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_latest_adapter,
            parent,
            false
        )
        return LatestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LatestViewHolder, position: Int) {
        holder.binding.latestMovie = movie.get(position)

        holder.itemView.animation =  AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation)

        if (!TextUtils.isEmpty(movie.get(position).poster_path)){
            holder.binding.posterPath.visibility = View.VISIBLE
            holder.binding.profilePath.visibility = View.GONE
        }
        else {
            holder.binding.posterPath.visibility = View.GONE
            holder.binding.profilePath.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyUID", Context.MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.putInt("userId", movie[position].id)
            myEdit.commit()

            var intent = Intent(context, TopRated::class.java)
            context.startActivity(intent)
        })

        Log.e("onBindViewHolder", "onBindViewHolder: " + movie.get(position).id)
    }

    override fun getItemCount(): Int {
        return movie.size
    }
}

