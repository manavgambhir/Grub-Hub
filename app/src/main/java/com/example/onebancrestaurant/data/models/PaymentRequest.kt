package com.example.onebancrestaurant.data.models

data class PaymentRequest(
    val total_amount: Double,
    val total_items: Int,
    val data: List<CartItemRequest>
)

data class CartItemRequest(
    val cuisine_id: Int,
    val item_id: Int,
    val item_price: Double,
    val item_quantity: Int
)

data class PaymentResult(
    val response_code: Int,
    val outcome_code: Int,
    val response_message: String,
    val txn_ref_no: String
)