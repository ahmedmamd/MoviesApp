package com.example.movieappkotlin.pojo

import com.google.gson.annotations.SerializedName

class ResultItem {

   @SerializedName("popularity")
     val popularity = 0.0
   @SerializedName("vote_average")
     var vote_average:kotlin.Float = 0.0f
   @SerializedName("vote_count")
     val vote_count:kotlin.Int = 0
   @SerializedName("id")
      var id:Int = 0
   @SerializedName("poster_path")
     val poster_path: String? = null
   @SerializedName("backdrop_path")
      var backdrop_path:kotlin.String? = null
   @SerializedName("original_language")
      var original_language:kotlin.String? = null
   @SerializedName("original_title")
      var original_title:kotlin.String? = null
   @SerializedName("title")
      var title:kotlin.String? = null
   @SerializedName("name")
      var name:kotlin.String? = null
   @SerializedName("first_air_date")
      var first_air_date:kotlin.String? = null
   @SerializedName("profile_path")
      var profile_path:kotlin.String? = null
   @SerializedName("overview")
      var overview:kotlin.String? = null
   @SerializedName("release_date")
      var release_date:kotlin.String? = null
   @SerializedName("adult")
      val adult = false
   @SerializedName("video")
      var video:kotlin.Boolean = false



}