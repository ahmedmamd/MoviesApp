package com.example.social.pojo.acount

data class Profile(
    var email:String,
    var password:String
) {
    var id:String = ""
    var userName:String? = null
}