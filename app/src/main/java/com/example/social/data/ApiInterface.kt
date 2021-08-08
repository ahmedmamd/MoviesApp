package com.example.movieappkotlin.data

import com.example.movieappkotlin.pojo.Myresponse
import com.example.movieappkotlin.pojo.ResultItem
import com.example.social.pojo.castAndCrew.CreditsResponse
import com.example.social.pojo.vedio.Vedio
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular")
    fun  popular(@Query("api_key") api_key:String,
                 @Query("language") language:String,
                 @Query("page") page:Int  ):Observable<Myresponse>

    @GET("movie/top_rated")
    fun  topRated(@Query("api_key") api_key:String,
                  @Query("language") language:String,
                  @Query("page") page:Int  ):Observable<Myresponse>

    @GET("movie/upcoming")
    fun  upcoming(@Query("api_key") api_key:String,
                  @Query("language") language:String,
                  @Query("page") page:Int  ):Observable<Myresponse>

    @GET("movie/{movie_id}")
    fun  detail(@Path("movie_id") id:Int,
                @Query("api_key") api_key:String,
                @Query("language") language:String,
                @Query("page") page:Int  ):Observable<ResultItem>

    @GET("movie/changes")
    fun changes(@Query("api_key") api_key:String,
                @Query("language") language:String,
                @Query("page") page:Int):Observable<Myresponse>

    @GET("trending/{media_type}/{time_window}")
    fun trending(@Path("media_type") media_type:String,
                 @Path("time_window") time_window:String,
                 @Query("api_key") api_key:String):Observable<Myresponse>

    @GET("movie/{movie_id}/credits")
    fun crew(@Path("movie_id") id:Int,
                 @Query("api_key") api_key:String,
                 @Query("language") language:String,
                 @Query("page") page:Int ):Observable<CreditsResponse>

    @GET("movie/{movie_id}/videos")
    fun vedio(@Path("movie_id") id:Int,
                 @Query("api_key") api_key:String,
                 @Query("language") language:String,
                 @Query("page") page:Int ):Observable<Vedio>

}