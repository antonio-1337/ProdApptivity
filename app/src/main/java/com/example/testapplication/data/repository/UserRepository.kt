package com.example.testapplication.data.repository

import androidx.annotation.WorkerThread
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

    // Get random quote
    suspend fun getRandomQuote(): Array<GetQuoteResponse> {
        return apiRequest { webApi.getRandomQuote() }
    }


    @WorkerThread
    // Save a task
    suspend fun saveTask(task: Tasks) = db.getTasksDao().upsert(task)

    // Update a task
    fun updateTask(task: Tasks) = db.getTasksDao().update(task)

    // Get a specific task
    fun getTask(id: Int) = db.getTasksDao().getTask(id)

    // Get all tasks for the day
    fun getDailyTasks(day: String) = db.getTasksDao().getDailyTasks(day)
}