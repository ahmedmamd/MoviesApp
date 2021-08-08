package com.example.social.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.social.R
import com.example.social.databinding.ActivityLoginBinding
import com.example.social.pojo.acount.Profile
import com.example.social.utils.Utils
import com.example.social.viewModell.AcountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable

class loginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    var acountViewModel: AcountViewModel? = null
    lateinit var profile: Profile

    var utils:Utils?= Utils()

    var emailObservable: Observable<Boolean>? = null
    var passwordObservable:Observable<Boolean>?=null

    var getEmailMutableLiveData = MutableLiveData<Boolean>()
    fun getEmailLiveData(): LiveData<Boolean>?{
        return getEmailMutableLiveData
    }
    var getPasswordMutableLiveData = MutableLiveData<Boolean>()
    fun getPasswordLiveData(): LiveData<Boolean>?{
        return getPasswordMutableLiveData
    }
    var check = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        setUpUi()
        setUpObserver()
    }

    private fun setUpUi() {
        validation()

        if (FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this,PopularMovie::class.java)
            startActivity(intent)
            finish()
        }

        binding.register.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        })
        binding.login.setOnClickListener(View.OnClickListener {
            signIn()
        })

    }

    private fun setUpObserver() {
        getEmailMutableLiveData.value=false
        getPasswordMutableLiveData.value=false

        getEmailLiveData()?.observe(this, Observer { boolean->
            check.value=true
        })
        getPasswordLiveData()?.observe(this, Observer { boolean->
            check.value=true
        })
        check.observe(this, Observer { boolean->
            binding.login.isEnabled = getEmailLiveData()?.value!! && getPasswordLiveData()?.value!!
        })
    }


    fun signIn() {
        acountViewModel = ViewModelProvider(this).get(AcountViewModel::class.java)
        acountViewModel!!.signIn(this,binding.email.text.toString(),binding.password.text.toString())
    }
    fun validation(){
        utils!!.validText(binding!!.email ,object : Utils.ValidEditText {
            override fun valid() {
                binding!!.emailInputLayout.error = "Invalid email"
                emailObservable = binding!!.email.textChanges()
                    .map { inputText: CharSequence ->
                        inputText.toString()
                            .matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())
                    }.distinctUntilChanged()
                emailObservable?.subscribe { isValid: Boolean? ->
                    binding!!.emailInputLayout.isErrorEnabled = !isValid!!
                    getEmailMutableLiveData.postValue(isValid)
                }
            }
        })

        utils!!.validText(binding!!.password, object : Utils.ValidEditText {
            override fun valid() {
                binding!!.passwordInputLayout.error = "Invalid passwod"
                passwordObservable =binding!!.password.textChanges()
                    .map { inputText:CharSequence->
                        inputText!!.toString().equals("")
                    }.distinctUntilChanged()
                passwordObservable?.subscribe{isVaild:Boolean?->
                    binding!!.passwordInputLayout.isErrorEnabled=isVaild!!
                    getPasswordMutableLiveData.postValue(!isVaild!!)
                }
            }
        })
    }
}