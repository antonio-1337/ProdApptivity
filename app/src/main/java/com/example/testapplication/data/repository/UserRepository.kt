package com.example.testapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.data.network.SafeApiRequest
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
        private val webApi: WebApi
): SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse{
        return apiRequest { webApi.loginUser(email,password)}
    }
}