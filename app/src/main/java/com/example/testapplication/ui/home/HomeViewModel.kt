package com.example.testapplication.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.repository.UserRepository
import utils.ApiException
import utils.Coroutines
import utils.NoInternetException

class HomeViewModel(
        private val userRepository: UserRepository
): ViewModel(){
    var name: String? = null
    var email: String? = null

    var homeListener: HomeListener? = null

    fun getRandomQuote(view: View){
        homeListener?.onStarted()

        //Main thread Scope
        Coroutines.main {
            try {
                val response = userRepository.getRandomQuote()
                response.let {

                    /*
                    val gson = Gson()
                    val arrayRandomQuoteType = object : TypeToken<Array<GetQuoteResponse>>() {}.type

                    var tutorials: Array<GetQuoteResponse> = gson.fromJson(it, arrayTutorialType)
                    */
                    homeListener?.onSuccess(it[0])
                    return@main
                }
                homeListener?.onError("message object is null")
            }catch (e: ApiException){
                homeListener?.onError(e.message!!)
            }catch (e: NoInternetException){
                homeListener?.onError(e.message!!)
            }
        }


    }

}