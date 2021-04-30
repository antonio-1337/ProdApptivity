package com.example.testapplication.data.network

import com.example.testapplication.data.network.responses.AuthResponse
import com.example.testapplication.data.network.responses.GetWaifuResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WebApi {
    //esempio chiamata api
    @FormUrlEncoded //serve nelle chiamate POST
    @POST(value = "login") //login è il nome della API
    suspend fun loginUser(
        @Field(value = "email") email_address: String, //parametro chiamata API = email
        @Field(value = "password") pass: String //parametro chiamata API = password
    ): Response<AuthResponse> //tipo di risposta della chiamata


    //Chiamata get random quote
    @GET(value = "sfw/waifu")
    suspend fun getRandomWaifu(): Response<GetWaifuResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): WebApi {

            var okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://waifu.pics/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WebApi::class.java)
       }
    }
}