// RegisterViewModel.kt
package com.example.handmade.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.handmade.Model.RegisterRequest
import com.example.handmade.Model.RegisterResponse
import com.example.handmade.Repository.AuthRepository

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepository() // Đảm bảo đã import đúng
    val registerResult = MutableLiveData<RegisterResponse>()

    fun registerUser(request: RegisterRequest) {
        repository.registerUser(request).observeForever {
            registerResult.value = it
        }
    }
}
