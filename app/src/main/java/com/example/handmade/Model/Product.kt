package com.example.handmade.Model
import java.io.Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val discount: Double,
    val image_url: String
): Serializable
