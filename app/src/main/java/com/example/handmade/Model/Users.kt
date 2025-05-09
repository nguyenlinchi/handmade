package com.example.handmade.Model

data class Users(
    val id: Int,
    val user_name: String,
    val name: String,
    val email: String,
    val password :String,
    val level: Int // <- thêm dòng này
)
