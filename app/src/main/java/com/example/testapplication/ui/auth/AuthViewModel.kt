package com.example.testapplication.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import utils.ApiException
import utils.Coroutines
import utils.NoInternetException

class AuthViewModel(
        private val userRepository: UserRepository
): ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginBtnClick(view: View){
        authListener?.onStarted()

        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onError("Invalid email or password")
            return
        }

        //Main thread Scope
        Coroutines.main {
            try {
                val response = userRepository.userLogin(email!!, password!!)
                response.message.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onError("message object is null")
            }catch (e: ApiException){
                authListener?.onError(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onError(e.message!!)
            }
        }


    }
}