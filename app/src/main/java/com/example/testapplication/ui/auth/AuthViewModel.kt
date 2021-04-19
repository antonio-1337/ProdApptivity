package com.example.testapplication.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import utils.ApiException
import utils.Coroutines
import utils.NoInternetException

class AuthViewModel: ViewModel() {
    var email: String? = null
    var password: String? = null
    var rememberMe: Boolean? = false

    var authListener: AuthListener? = null

    fun onLoginBtnClick(view: View){
        authListener?.onStarted()

        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onError("Invalid email or password")
            return
        }
        //faccio la chiamata api TODO cambiare modo di fare la chiamata


        //Main thread Scope
        Coroutines.main {
            try {
                val response = UserRepository().userLogin(email!!, password!!)
                response.message.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onError("message object is null")
            }catch (e: ApiException){
                authListener?.onError(e.message!!)
            }
        }


    }
}