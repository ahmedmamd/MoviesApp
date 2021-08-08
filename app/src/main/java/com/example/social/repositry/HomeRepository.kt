package com.example.social.repositry

import android.content.Context
import android.content.SharedPreferences
import com.example.movieappkotlin.data.ApiInterface
import com.example.movieappkotlin.data.ApiServices
import com.example.movieappkotlin.pojo.Myresponse
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.pojo.castAndCrew.CreditsResponse
import com.example.social.pojo.vedio.Vedio
import com.example.social.utils.Keys
import io.reactivex.rxjava3.core.Observable

class HomeRepository(context: Context?) {
    var apiInterface: ApiInterface? = null
    val API_KEY = "9c02c9ddb0ce19bf0dabd869597c0aaf"
    val PAGE = 1
    val LANGUAGE = "en-US"



    val sharedPreference:SharedPreferences =context!!.applicationContext!!.getSharedPreferences("MyUID", Context.MODE_PRIVATE)
    val sharedPreferencemedia_type:SharedPreferences =context!!.applicationContext.getSharedPreferences(Keys.filter, Context.MODE_PRIVATE)


     var resultItem:ResultItem = ResultItem()

    fun popular(context: Context?): Observable<Myresponse> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.popular(API_KEY, LANGUAGE, PAGE)
    }

    fun topRated(context: Context?): Observable<Myresponse> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.topRated(API_KEY, LANGUAGE, PAGE)
    }

    fun upcoming(context: Context?): Observable<Myresponse> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.upcoming(API_KEY, LANGUAGE, PAGE)
    }

    fun details(context: Context?): Observable<ResultItem> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.detail(sharedPreference.getInt("userId",0),API_KEY, LANGUAGE, PAGE)
    }

    fun latest(context: Context?): Observable<Myresponse> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.changes(API_KEY, LANGUAGE , PAGE )
    }

    fun trending(context: Context?): Observable<Myresponse> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.trending(sharedPreferencemedia_type.getString(Keys.media_type,"")!! , sharedPreferencemedia_type.getString(Keys.time_window,"")!! ,API_KEY)
    }

    fun crew(context: Context?): Observable<CreditsResponse> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.crew(sharedPreference.getInt("userId",0),API_KEY,LANGUAGE,PAGE)
    }

    fun vedio(context: Context?): Observable<Vedio> {
        apiInterface = ApiServices().getINSTANCE(context)
        return apiInterface!!.vedio(sharedPreference.getInt("userId",0),API_KEY,LANGUAGE,PAGE)
    }
}