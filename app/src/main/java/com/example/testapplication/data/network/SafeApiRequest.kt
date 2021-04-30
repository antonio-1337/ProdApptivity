package com.example.testapplication.data.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {
    suspend fun <T: Any> apiRequest(call: suspend ()-> Response<T>): T{
        val response = call.invoke()

        if(response.isSuccessful){
            return response.body()!!
        }
        else{
            val error = response.errorBody()?.string()
            val message = StringBuilder()

            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                }catch (err: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw utils.ApiException(message.toString())
        }
    }
}