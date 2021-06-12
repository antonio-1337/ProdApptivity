package com.example.testapplication.data.repository

import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.network.SafeApiRequest
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.network.responses.AuthResponse
import com.example.testapplication.data.network.responses.GetQuoteResponse

class UserRepository(
        private val webApi: WebApi,
        private val db: AppDatabase
): SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse{
        return apiRequest { webApi.loginUser(email,password)}
    }

    //get random quote
    suspend fun getRandomQuote(): Array<GetQuoteResponse> {
        return apiRequest { webApi.getRandomQuote() }
    }

    //save or update a task
    suspend fun saveTask(tasks: Tasks) = db.getTasksDao().upsert(tasks)




}