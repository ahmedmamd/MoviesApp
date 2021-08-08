package com.example.social.pojo.castAndCrew

import com.google.gson.annotations.SerializedName

class CreditsResponse {
    @SerializedName( "id")
    val  id:Int?=null
    @SerializedName("cast")
    var cast:ArrayList<CastAndCrew>?=null
    @SerializedName("crew")
    var crew:ArrayList<CastAndCrew>?=null
}
