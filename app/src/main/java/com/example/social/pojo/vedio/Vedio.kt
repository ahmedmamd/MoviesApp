package com.example.social.pojo.vedio

import com.example.movieappkotlin.pojo.ResultItem
import com.google.gson.annotations.SerializedName

class Vedio {

    @SerializedName("id")
    var id:Int = 0
    @SerializedName("results")
    var results:ArrayList<Result_vedio>?=null
}