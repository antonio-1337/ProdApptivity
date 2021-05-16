package com.example.testapplication.data.network.responses

data class GetQuoteResponse(
    var q: String, //quote
    var a: String, //author
    val h: String //HTML format
)
