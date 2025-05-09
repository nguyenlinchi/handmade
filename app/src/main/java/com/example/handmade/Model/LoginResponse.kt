package com.example.handmade.Model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: Users?
)

