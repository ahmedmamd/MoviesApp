package com.example.social.ui


import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.social.R
import com.example.social.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        lateinit var binding:ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
                binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
                setUpUi()
    }

    private fun setUpUi() {
        val intent = Intent(this, loginActivity::class.java)
        startActivity(intent)
         finish()
    }


}
