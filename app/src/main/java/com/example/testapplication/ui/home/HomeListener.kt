package com.example.testapplication.ui.home

import com.example.testapplication.data.network.responses.GetQuoteResponse

interface HomeListener {
    fun onStarted()
    fun onSuccess(response: GetQuoteResponse)
    fun onError(errorMsg: String)
}