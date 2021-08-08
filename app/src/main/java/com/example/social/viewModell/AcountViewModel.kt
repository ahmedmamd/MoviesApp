package com.example.social.viewModell

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.social.base.BaseViewModel
import com.example.social.pojo.acount.Profile
import com.example.social.ui.PopularMovie
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AcountViewModel: BaseViewModel() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference

    var userIdLiveData = MutableLiveData<String>()
    fun observeUserId(): LiveData<String>? {
        return userIdLiveData
    }

    fun createUserProfile(context: Context, profile: Profile) {
        val sharedPreferences = context.getSharedPreferences("MyUID", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putString("userId", profile.id)
        myEdit.commit()
        //uploadImage(context, filePath)
//        databaseReference = FirebaseDatabase.getInstance().getReference()
        databaseReference =
            Firebase.database.reference.child("Users").child(profile.id)
            databaseReference.setValue(profile)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
               //     creationProfileLiveData.setValue("User Creation Successfully")
                } else {
                 //   creationProfileLiveData.setValue("User Creation Failed")
                    Log.e("createUserProfile", "failed: " + task.exception!!.message)
                }
            })
    }

    fun createUserAccount(context: Context?, email: String, password: String) {
        mAuth = Firebase.auth
        Log.e("mAuth", ""+mAuth )
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        Log.d("isSuccessful", "createUserWithEmail:success")
                        val userId = task.result!!.user!!.uid
                            userIdLiveData.setValue(userId)
                    } else {
                        Log.e("createUserAccount","failed: "+ task.exception!!.message)
                    }
                })
    }
    fun signIn(context: Context?, email: String, password: String){
        mAuth = Firebase.auth
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("signIn", "signInWithEmail:success")
                    var intent=Intent(context , PopularMovie::class.java)
                         context.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("signIn", "signInWithEmail:failure", task.exception)
                }
            }
    }
}