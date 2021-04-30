package com.example.testapplication.ui.home

interface HomeListener {
    fun onStarted()
    fun onSuccess(response: String)
    fun onError(errorMsg: String)
}