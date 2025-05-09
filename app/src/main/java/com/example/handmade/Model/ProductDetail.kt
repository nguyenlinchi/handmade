package com.example.handmade.Model

data class ProductDetail(
    val id: String,
    val name: String,
    val price: String,
    val discount: String,
    val description: String,
    val brand_name: String,
    val brand_image: String,
    val images: List<String>,
    val sizes: List<String>
)