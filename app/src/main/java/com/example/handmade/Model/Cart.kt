package com.example.handmade.Model

data class Cart(
    val user_id: Int,
    val product_id: Int,
    val product_name: String,
    val product_image: String,
    val product_price: Double,
    val size: String,
    var quantity: Int
)

