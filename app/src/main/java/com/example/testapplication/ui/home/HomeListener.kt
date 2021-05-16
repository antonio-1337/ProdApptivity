package com.example.testapplication.ui.home

import com.example.testapplication.data.network.responses.GetQuoteResponse

interface HomeListener {
    fun onStarted()
    fun onSuccess(getQuoteResponse: GetQuoteResponse)
    fun onError(errorMsg: String)
}