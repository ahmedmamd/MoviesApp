package com.example.social.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.social.R
import com.example.social.databinding.ActivityRegisterBinding
import com.example.social.pojo.acount.Profile
import com.example.social.utils.Utils
import com.example.social.viewModell.AcountViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    var acountViewModel: AcountViewModel? = null
    lateinit var profile: Profile

    var utils: Utils?= Utils()

    var getUserNameMutableLiveData = MutableLiveData<Boolean>()
    fun getUserNameLiveData(): LiveData<Boolean?>? {
        return getEmailMutableLiveData
    }
    var getEmailMutableLiveData = MutableLiveData<Boolean>()
    fun getEmailLiveData(): LiveData<Boolean?>? {
        return getEmailMutableLiveData
    }
    var getPasswordMutableLiveData = MutableLiveData<Boolean>()
    fun getPasswordLiveData(): LiveData<Boolean?>?{
        return getPasswordMutableLiveData
    }
    var getConfirmPasswordMutableLiveData = MutableLiveData<Boolean>()
    fun getConfirmPasswordLiveData(): LiveData<Boolean?>?{
        return getConfirmPasswordMutableLiveData
    }
    var password = MutableLiveData<CharSequence>()
    var check = MutableLiveData<Boolean>()

    var passwordObservable: Observable<Boolean>?=null
    var confirmPasswordObservable: Observable<Boolean>? = null
    var emailObservable: Observable<Boolean>? = null
    var userNameObservable: Observable<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        acountViewModel = ViewModelProvider(this).get(AcountViewModel::class.java)

        setUPUi()
        setUPObserver()
    }

    private fun setUPUi() {
        validation()
        binding.register.setOnClickListener(View.OnClickListener {
            signup()
        })
        binding.loginPage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@RegisterActivity , loginActivity::class.java)
            startActivity(intent)
        })
    }
    private fun setUPObserver() {
        getEmailMutableLiveData.value = false
        getPasswordMutableLiveData.value = false
        getConfirmPasswordMutableLiveData.value =false
        getUserNameMutableLiveData.value =false

        getUserNameLiveData()?.observe(this, androidx.lifecycle.Observer { boolean ->
            check.value = true
        })
        getEmailLiveData()?.observe(this, androidx.lifecycle.Observer { boolean ->
            check.value = true
        })
        getPasswordLiveData()?.observe(this, androidx.lifecycle.Observer { boolean ->
            check.value = true
        })
        getConfirmPasswordMutableLiveData.observe(this, androidx.lifecycle.Observer { boolean ->
            check.value = true
        })
        check.observe(this, androidx.lifecycle.Observer { check ->
            if(binding.password.text!!.toString().equals(binding.confirmPassword.text.toString())){
                if (getEmailLiveData()!!.value!! && getPasswordLiveData()!!.value!! && getConfirmPasswordLiveData()!!.value!! && getUserNameLiveData()!!.value!!) {
                    binding!!.register.isEnabled = true
                } else binding!!.register!!.isEnabled = false
            }else binding!!.register!!.isEnabled = false
        })
        acountViewModel!!.observeUserId()?.observe(this, Observer { id->
            profile.id = id
            profile.userName = binding.userName.text.toString()
            acountViewModel?.createUserProfile(this,profile)
        })
    }

    private fun signup() {
        profile = Profile(binding.email.text.toString(),binding.password.text.toString(),)
        acountViewModel!!.createUserAccount(this, profile.email, profile.password)
    }

    fun validation(){
        utils!!.validText(binding!!.email, object : Utils.ValidEditText {
            override fun valid() {
                binding!!.emailInputLayout.error = "Invalid email"
                emailObservable = binding!!.email.textChanges()
                    .map { inputText: CharSequence ->
                        inputText.toString()
                            .matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())
                    }
                    .distinctUntilChanged()
                emailObservable?.subscribe { isValid: Boolean? ->
                    binding!!.emailInputLayout.isErrorEnabled = !isValid!!
                }
                emailObservable?.subscribe { isValid: Boolean? ->
                    getEmailMutableLiveData.postValue(isValid)
                }
            }
        })
        utils!!.validText(binding!!.password, object : Utils.ValidEditText {
            override fun valid() {
                password.postValue(binding?.password?.text)
                binding!!.passwordInputLayout.error = "Invalid password"
                passwordObservable = binding!!.password.textChanges()
                    .map { inputText: CharSequence ->
                        inputText.toString().matches("^(?=.*\\d).{4,8}$".toRegex())
                    }
                    .distinctUntilChanged()
                passwordObservable?.subscribe { isVaild: Boolean? ->
                    binding!!.passwordInputLayout.isErrorEnabled = !isVaild!!
                }
                passwordObservable?.subscribe { isValid: Boolean? ->
                    getPasswordMutableLiveData.postValue(isValid)
                }
            }
        })

        utils!!.validText(binding!!.confirmPassword, object : Utils.ValidEditText {
            override fun valid() {
                binding!!.confirmPasswordInputLayout.error = "Invalid password"
                confirmPasswordObservable = binding!!.confirmPassword.textChanges()
                    .map { inputText: CharSequence ->
                        inputText.toString().equals(password.value.toString())
                    }.distinctUntilChanged()
                confirmPasswordObservable?.subscribe { isVaild: Boolean? ->
                    binding!!.confirmPasswordInputLayout.isErrorEnabled = !isVaild!!
                }
                confirmPasswordObservable?.subscribe { isValid: Boolean? ->
                    getConfirmPasswordMutableLiveData.postValue(isValid)
                }
            }
        })

        utils!!.validText(binding!!.userName,object : Utils.ValidEditText{
            override fun valid() {
                binding.userNameInputLayout.error = "enter your user name "
                userNameObservable =binding!!.userName.textChanges()
                    .map { inputText : CharSequence->
                        inputText.toString().equals("")
                    }.distinctUntilChanged()
                userNameObservable?.subscribe{ isValid :Boolean? ->
                    binding?.userNameInputLayout.isErrorEnabled =isValid!!
                    getUserNameMutableLiveData.postValue(isValid)
                }
            }
        })
    }
}