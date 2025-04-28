package com.example.onebancrestaurant

data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    val imageUrl: String
)