package com.example.social.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.R
import com.example.social.adapter.CastAdapter
import com.example.social.adapter.CrewAdapter
import com.example.social.adapter.VedioAdapter
import com.example.social.databinding.ActivityTopRatedBinding
import com.example.social.pojo.castAndCrew.CastAndCrew
import com.example.social.pojo.castAndCrew.CreditsResponse
import com.example.social.pojo.vedio.Result_vedio
import com.example.social.pojo.vedio.Vedio
import com.example.social.viewModell.HomeViewModel
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper


class TopRated : AppCompatActivity(){
    lateinit var binding:ActivityTopRatedBinding
    var homeViewModel: HomeViewModel?=null

    var castMovieList = ArrayList<CastAndCrew>()
    var castAdapter = CastAdapter(castMovieList, this)

    var crewMovieList = ArrayList<CastAndCrew>()
    var crewAdapter = CrewAdapter(crewMovieList, this)

    var videoMovieList = ArrayList<Result_vedio>()
    var videoAdapter = VedioAdapter(videoMovieList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_rated)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setUPUi()
        setUPObserver()
    }
    private fun setUPUi() {

        homeViewModel!!.details(this)
        homeViewModel!!.cast(this)
        homeViewModel!!.video(this)

        binding.castRec.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.castRec.adapter = castAdapter
        GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.castRec)

        binding.crewRec.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.crewRec.adapter = crewAdapter
        GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.crewRec)

        binding.videoRec.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.videoRec.adapter = videoAdapter
        GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.videoRec)

    }

    private fun setUPObserver() {

        homeViewModel!!.detailsLiveData()!!.observe(this, Observer<ResultItem?> { response ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original" + response.backdrop_path)
                .placeholder(R.drawable.defult)
                .into(binding.posterPath)
            binding.title.text = response.title
            binding.voteAverage.text = response.vote_average.toString()
            binding.releaseDate.text = response.release_date
            binding.overview.text = response.overview
        })

        homeViewModel!!.castLiveData()!!.observe(this, Observer<CreditsResponse?> { response ->
            castMovieList.clear()
            crewMovieList.clear()
            response.cast?.let { castMovieList.addAll(it) }
            response.crew?.let { crewMovieList.addAll(it) }
            castAdapter.notifyDataSetChanged()
            crewAdapter.notifyDataSetChanged()
        })

        homeViewModel!!.vedioLiveData()!!.observe(this, Observer<Vedio?> { response ->
            videoMovieList.clear()
            response.results?.let { videoMovieList.addAll(it) }
            videoAdapter.notifyDataSetChanged()

        })
    }
}