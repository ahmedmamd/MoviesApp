package com.example.social.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.adroitandroid.chipcloud.ChipListener
import com.example.movieappkotlin.pojo.Myresponse
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.R
import com.example.social.adapter.LatestMovieAdapter
import com.example.social.adapter.PopularMovieAdapter
import com.example.social.databinding.ActivityPopularMovieBinding
import com.example.social.utils.Keys
import com.example.social.viewModell.HomeViewModel
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.firebase.auth.FirebaseAuth
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class PopularMovie : AppCompatActivity(), ChipListener {
    lateinit var binding: ActivityPopularMovieBinding

    val names = arrayOf("popular", "top_rated", "latest")
    var movieList = ArrayList<ResultItem>()
    var latestMovieList = ArrayList<ResultItem>()
    var latestAdapter = LatestMovieAdapter(latestMovieList, this)
    var adapter = PopularMovieAdapter(movieList, this)
    var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popular_movie)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setUPUi()
        setUPObserver()

    }

    private fun setUPUi() {

//        setSupportActionBar(binding.myToolbar)
        binding.myToolbar.inflateMenu(R.menu.setting)
        binding.myToolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener,
            androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item?.getItemId() == R.id.logout) {
                    AlertDialog.Builder(this@PopularMovie)
                        .setTitle("Logout entry")
                        .setMessage("Are you sure you want to logout ?")
                        .setPositiveButton(android.R.string.yes,
                            DialogInterface.OnClickListener { dialog, which ->
                                FirebaseAuth.getInstance().signOut()
                                val intent = Intent(this@PopularMovie, loginActivity::class.java)
                                startActivity(intent)
                            })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
                return true
            }
        })



        val spinermedia_type: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, listOf(
                "all",
                "movie",
                "tv",
                "person"
            )
        )
            binding.mediaType.setAdapter(spinermedia_type)
        binding.mediaType.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (parent?.getItemAtPosition(position).toString().equals("all")){
                    saveFilter(Keys.media_type, "all")
                    homeViewModel!!.trending(this@PopularMovie)
                } else if (parent?.getItemAtPosition(position).toString().equals("movie")){
                    saveFilter(Keys.media_type, "movie")
                    homeViewModel!!.trending(this@PopularMovie)
                }  else  if (parent?.getItemAtPosition(position).toString().equals("tv")){
                    saveFilter(Keys.media_type, "tv")
                    homeViewModel!!.trending(this@PopularMovie)

                }  else  if (parent?.getItemAtPosition(position).toString().equals("person")){
                    saveFilter(Keys.media_type, "person")
                    homeViewModel!!.trending(this@PopularMovie)
                }
            }
        }

        val  spinertime_window: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, listOf(
                "day",
                "week"
            )
        )
             binding.timeWindow.setAdapter(spinertime_window)
        binding.timeWindow.onItemClickListener = object :AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent?.getItemAtPosition(position).toString().equals("day")){
                    saveFilter(Keys.time_window, "day")
                    homeViewModel!!.trending(this@PopularMovie)
                } else if (parent?.getItemAtPosition(position).toString().equals("week")){
                    saveFilter(Keys.time_window, "week")
                    homeViewModel!!.trending(this@PopularMovie)
                }
            }
        }

        saveFilter(Keys.media_type, "all")
        saveFilter(Keys.time_window, "day")

        homeViewModel!!.getPopularMovie(this)
        homeViewModel!!.trending(this)


        binding.trendingRec.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingRec.adapter = latestAdapter
        GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.trendingRec)

        binding.recMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recMovie.adapter = adapter
        GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.recMovie)

        binding.chipCloud.addChips(names)
        binding.chipCloud.setSelectedChip(0)
//        binding.chipCloud.setBackgroundColor()
        binding.chipCloud.setChipListener(this)

    }

    private fun setUPObserver() {

        homeViewModel!!.latestLiveData()!!.observe(this, Observer<Myresponse?> { response ->
            latestMovieList.clear()
            latestMovieList.addAll(response.results)
            Log.e("trending", "setUPObserver: " + response)
            latestAdapter.notifyDataSetChanged()
        })

        homeViewModel!!.popularLiveData()!!.observe(this, Observer<Myresponse?> { response ->
            movieList.clear()
            movieList.addAll(response.results)
            adapter.notifyDataSetChanged()
        })

        homeViewModel!!.topRatedLiveData()!!.observe(this, Observer<Myresponse?> { response ->
            movieList.clear()
            movieList.addAll(response.results)
            adapter.notifyDataSetChanged()
        })

        homeViewModel!!.upcomingLiveData()!!.observe(this, Observer<Myresponse?> { response ->
            movieList.clear()
            movieList.addAll(response.results)
            adapter.notifyDataSetChanged()
        })
    }

    override fun chipSelected(index: Int) {
        if (index == 0) {
            homeViewModel!!.getPopularMovie(this)
            Log.e("chipSelected", "chipSelected:0 ")
        } else if (index == 1) {
            homeViewModel!!.getTopRated(this)
            Log.e("chipSelected", "chipSelected: 1")
        } else if (index == 2) {
            homeViewModel!!.upcoming(this)
            Log.e("chipSelected", "chipSelected: 2")
        }
    }

    override fun chipDeselected(index: Int) {
    }

    fun saveFilter(key: String, mValue: String){
        val sharedPreferences = this@PopularMovie.getSharedPreferences(Keys.filter, MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putString(key, mValue)
        myEdit.commit()
    }
}