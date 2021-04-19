package com.example.testapplication.data.network

import com.example.testapplication.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WebApi {
    //esempio chiamata api
    @FormUrlEncoded //serve nelle chiamate POST
    @POST(value = "login") //login è il nome della API
    suspend fun loginUser(
        @Field(value = "email") email_address: String, //parametro chiamata API = email
        @Field(value = "password") pass: String //parametro chiamata API = password
    ): Response<AuthResponse> //tipo di risposta della chiamata

    companion object {
        operator fun invoke(): WebApi {
            return Retrofit.Builder()
                    .baseUrl("https://baseurlusedforapicall")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WebApi::class.java)
       }
    }
}