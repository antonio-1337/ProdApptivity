package com.example.testapplication.data.repository

import com.example.testapplication.data.network.SafeApiRequest
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.network.responses.AuthResponse
import com.example.testapplication.data.network.responses.GetQuoteResponse
import org.kodein.di.TypeToken

class UserRepository(
        private val webApi: WebApi
): SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse{
        return apiRequest { webApi.loginUser(email,password)}
    }

    //get random quote
    suspend fun getRandomQuote(): Array<GetQuoteResponse> {
        return apiRequest { webApi.getRandomQuote() }
    }
}