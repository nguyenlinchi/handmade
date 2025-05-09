package com.example.handmade.Model

data class OrderRequest(
    val user_id: Int,
    val delivery_address: String,
    val phone_number: String,
    val total_amount: Double,
    val payment_method: String,
    val note: String,
    val order_items: List<OrderItem>
)

