package com.example.social.adapter

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.R
import com.example.social.databinding.ItemMovieBinding
import com.example.social.ui.TopRated

class PopularMovieAdapter(
        var movie: ArrayList<ResultItem>,
        val context: Context
): RecyclerView.Adapter<PopularMovieAdapter.MovieVoiwHolder>() {
    class MovieVoiwHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVoiwHolder {
        val  binding : ItemMovieBinding
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
        return MovieVoiwHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieVoiwHolder, position: Int) {
            holder.binding.movie = movie.get(position)
            holder.itemView.animation =  AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation)
            holder.itemView.setOnClickListener(View.OnClickListener {
                val sharedPreferences = context.getSharedPreferences("MyUID", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()
                myEdit.putInt("userId",movie[position].id)
                myEdit.commit()

                var intent = Intent(context, TopRated::class.java)
                context.startActivity(intent)
            })
    }
    override fun getItemCount(): Int {
      return movie.size
    }
}


