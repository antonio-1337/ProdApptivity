package com.example.testapplication.ui.home

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.main.MainContainerActivity
import utils.ApiException
import utils.Coroutines
import utils.NoInternetException

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var homeListener: HomeListener? = null


    fun gotoMainpage(view: View) {
        Intent(view.context, MainContainerActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun getRandomQuote() {
        homeListener?.onStarted()

        //Main thread Scope
        Coroutines.main {
            try {
                val response = userRepository.getRandomQuote()
                response.let {
                    //metto la citazione fra doppi apici
                    it[0].q = "\"" + it[0].q + "\""
                    it[0].a = "- " + it[0].a
                    homeListener?.onSuccess(it[0])
                    return@main
                }
                homeListener?.onError("message object is null")
            } catch (e: ApiException) {
                homeListener?.onError(e.message!!)
            } catch (e: NoInternetException) {
                homeListener?.onError(e.message!!)
            }
        }


    }


}