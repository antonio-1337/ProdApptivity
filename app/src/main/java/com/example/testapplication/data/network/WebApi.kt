package com.example.testapplication.data.network

import com.example.testapplication.data.network.responses.AuthResponse
import com.example.testapplication.data.network.responses.GetQuoteResponse
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WebApi {
    //esempio chiamata api POST
    @FormUrlEncoded //serve nelle chiamate POST
    @POST(value = "login") //login è il nome della API
    suspend fun loginUser(
        @Field(value = "email") email_address: String, //parametro chiamata API = email
        @Field(value = "password") pass: String //parametro chiamata API = password
    ): Response<AuthResponse> //tipo di risposta della chiamata


    //@GET(value ="random")
    //suspend fun getRandomQuote(): Response<GetQuoteResponse>

    @GET(value ="random")
    suspend fun getRandomQuote(): Response<Array<GetQuoteResponse>>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): WebApi {

            var okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://zenquotes.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WebApi::class.java)
       }
    }
}