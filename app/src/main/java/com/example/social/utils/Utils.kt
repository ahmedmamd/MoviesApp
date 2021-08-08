package com.example.social.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.social.R

open  class Utils {
    open interface ValidEditText{
        fun valid()
    }

    open fun validText(editText: EditText, validEditText: ValidEditText){
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validEditText.valid()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}
    @BindingAdapter("imageBinding")
     fun bindUser(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load("https://image.tmdb.org/t/p/original"+imageUrl)
            .placeholder(R.drawable.defult)
            .into(view)
    }
    @BindingAdapter("imageVideoBinding")
         fun bindVideo(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load("https://img.youtube.com/vi/"+imageUrl+"/hqdefault.jpg")
                .placeholder(R.drawable.boarder_cycle)
                .into(view)
        }
//"http://img.youtube.com/vi/"+imageUrl+"/hqdefault.jpg"