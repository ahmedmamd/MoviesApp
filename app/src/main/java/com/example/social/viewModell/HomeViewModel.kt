package com.example.social.viewModell

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappkotlin.data.ApiInterface
import com.example.movieappkotlin.data.ApiServices
import com.example.movieappkotlin.pojo.Myresponse
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.pojo.castAndCrew.CreditsResponse
import com.example.social.pojo.vedio.Vedio
import com.example.social.repositry.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel : ViewModel() {
    var homeRepository: HomeRepository? = null
    var apiInterface: ApiInterface? = null

    var popularMutableLiveData: MutableLiveData<Myresponse?> = MutableLiveData<Myresponse?>()

    fun popularLiveData(): LiveData<Myresponse?>? {
        return popularMutableLiveData
    }

    var topRatedMutableLiveData: MutableLiveData<Myresponse?> = MutableLiveData<Myresponse?>()

    fun topRatedLiveData(): LiveData<Myresponse?>? {
        return topRatedMutableLiveData
    }

    var upcomingMutableLiveData: MutableLiveData<Myresponse?> = MutableLiveData<Myresponse?>()

    fun upcomingLiveData(): LiveData<Myresponse?>? {
        return upcomingMutableLiveData
    }
    var detailsMutableLiveData: MutableLiveData<ResultItem?> = MutableLiveData<ResultItem?>()

    fun detailsLiveData(): LiveData<ResultItem?>? {
        return detailsMutableLiveData
    }
    var latestMutableLiveData: MutableLiveData<Myresponse?> = MutableLiveData<Myresponse?>()

    fun latestLiveData(): LiveData<Myresponse?>? {
        return latestMutableLiveData
    }
    var castMutableLiveData: MutableLiveData<CreditsResponse?> = MutableLiveData<CreditsResponse?>()

    fun castLiveData(): LiveData<CreditsResponse?>? {
        return castMutableLiveData
    }
    var vedioMutableLiveData: MutableLiveData<Vedio?> = MutableLiveData<Vedio?>()

    fun vedioLiveData(): LiveData<Vedio?>? {
        return vedioMutableLiveData
    }

    fun getPopularMovie(context: Context?) {
        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)

        homeRepository!!.popular(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<Myresponse?> {
                override fun onSubscribe(d: @NonNull Disposable?) {}
                override fun onNext(myResponse: @NonNull Myresponse?) {
                    popularMutableLiveData.setValue(myResponse)
                }
                override fun onError(e: @NonNull Throwable?) {
                    Log.e("getPopularMovie", "onError: " + e?.message)
                }
                override fun onComplete() {}
            })
    }

    fun getTopRated(context: Context?){
        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)

        homeRepository!!.topRated(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<Myresponse?>{
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(response: Myresponse?) {
                   topRatedMutableLiveData.postValue(response)
                    Log.e("getTopRatedMovie", "onResponse: " + response!!.results[0].id)
                }
                override fun onError(e: Throwable?) {
                    Log.e("getTopRatedMovie", "onError: " + e?.message)
                }

                override fun onComplete() {
                }

            })
    }

    fun upcoming(context: Context?){
        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)

        homeRepository!!.upcoming(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<Myresponse?>{
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(response: Myresponse?) {
                   topRatedMutableLiveData.postValue(response)
                    Log.e("getUpcomingMovie", "onResponse: " + response?.results)
                }
                override fun onError(e: Throwable?) {
                    Log.e("getUpcomingMovie", "onError: " + e?.message)
                }
                override fun onComplete() {
                }
            })
    }
    fun details(context: Context?){
        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)
        homeRepository!!.details(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<ResultItem?>{
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(response: ResultItem?) {
                   detailsMutableLiveData.postValue(response)
                    Log.e("detail", "onResponse: " + response?.id)
                }
                override fun onError(e: Throwable?) {
                    Log.e("detail", "onError: " + e?.message)
                }
                override fun onComplete() {
                }
            })
    }

    fun latest(context: Context?){
        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)
        homeRepository!!.latest(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<Myresponse?>{
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(response: Myresponse?) {
                  latestMutableLiveData.postValue(response)
                    Log.e("latest", "onResponse: " + response!!.results[0].id)
                }
                override fun onError(e: Throwable?) {
                    Log.e("latest", "onError: " + e?.message)
                }
                override fun onComplete() {
                }
            })
    }

    fun trending(context: Context?){
        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)
        homeRepository!!.trending(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<Myresponse?>{
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(response: Myresponse?) {
                    latestMutableLiveData.postValue(response)
                    Log.e("trending", "onResponse: "+response!!.results[0].id)
                }
                override fun onError(e: Throwable?) {
                    Log.e("trending", "onError: "+ e?.message)
                }
                override fun onComplete() {
                }
            })
    }

    fun cast(context: Context?){

        apiInterface = ApiServices().getINSTANCE(context)
        homeRepository = HomeRepository(context)
        homeRepository!!.crew(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(object : Observer<CreditsResponse?>{
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(response: CreditsResponse?) {
                    castMutableLiveData.postValue(response)
                    Log.e("cast", "onResponse: "+ response?.cast!![0].name)
                }
                override fun onError(e: Throwable?) {
                    Log.e("crew", "onError: "+ e?.message)
                }
                override fun onComplete() {
                }
            })
    }


      fun video(context: Context?) {

          apiInterface = ApiServices().getINSTANCE(context)
          homeRepository = HomeRepository(context)
          homeRepository!!.vedio(context)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .unsubscribeOn(Schedulers.io())
              .subscribe(object : Observer<Vedio?> {
                  override fun onSubscribe(d: Disposable?) {
                  }

                  override fun onNext(response: Vedio?) {
                      vedioMutableLiveData.postValue(response)
                  }

                  override fun onError(e: Throwable?) {
                      Log.e("video", "onError: " + e?.message)
                  }

                  override fun onComplete() {
                  }

              })
      }

}