package com.example.handmade.Model

data class UpdateCartRequest(
    val productId: Int,
    val quantity: Int,
    val userId: Int
)
