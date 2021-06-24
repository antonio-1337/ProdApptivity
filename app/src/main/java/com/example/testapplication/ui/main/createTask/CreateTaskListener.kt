package com.example.testapplication.ui.main.createTask

interface CreateTaskListener {
    fun onStarted()
    fun onSuccess(okMsg: String)
    fun onError(errorMsg: String)
}