package com.example.handmade.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.handmade.Model.RegisterResponse
import com.example.handmade.Model.RegisterRequest
import com.example.handmade.Rest.RetrofitClient


class AuthRepository {
    fun registerUser(request: RegisterRequest): LiveData<RegisterResponse> {
        val data = MutableLiveData<RegisterResponse>()
        RetrofitClient.api.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                data.value = RegisterResponse(false, "Network error: ${t.message}")
            }
        })
        return data
    }
}