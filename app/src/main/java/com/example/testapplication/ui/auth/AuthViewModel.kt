package com.example.testapplication.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.repository.UserRepository

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
        val response = UserRepository().userLogin(email!!, password!!)

        authListener?.onSuccess(response)
        // TO DO: navigate to homepage
        //test primo commin da android studio
    }
}