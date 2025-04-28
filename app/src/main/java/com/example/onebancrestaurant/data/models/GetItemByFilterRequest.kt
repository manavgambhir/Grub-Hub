package com.example.onebancrestaurant.data.models

data class GetItemByFilterRequest(
    val cuisineType: List<String>? = null,
    val priceRange: PriceRange? = null,
    val minRating: Double? = null
)

data class PriceRange(
    val minAmount: Double,
    val maxAmount: Double
)
