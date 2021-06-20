package com.example.testapplication.ui.home

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getRandomQuote() {
        homeListener?.onStarted()

        //Main thread Scope
        Coroutines.main {
            try {
                val response = userRepository.getRandomQuote()
                response.let {
                    //Serve a far apparire la citazione fra doppi apici
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

    /* Gestisce la navigazione verso la MainContainerActivity.
    So che sembra un giro pittosto strano, ma queste sono le coding guidelines dettate dalla stessa Google
    e, per mantenere coerenza in tutto il progetto, le seguirò.
    Il senso è che è compito del ViewModel gestire la navigazione, che viene però implementata dallo UI Controller
    (in questo caso la HomeActivity). Quindi il ViewModel all'avvento del click "a "l'ordine" alla Activity di
    navigare verso la pagina desiderata. */
    private val _toMainPage = MutableLiveData<Boolean>()
    val toMainPage: LiveData<Boolean>
        get() = _toMainPage

    fun goToMainPage(){
        _toMainPage.value = true
    }
}