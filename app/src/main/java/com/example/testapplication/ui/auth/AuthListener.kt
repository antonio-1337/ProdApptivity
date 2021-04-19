package com.example.testapplication.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(response: String)
    fun onError(errorMsg: String)
}