package com.example.onebancrestaurant.data.models

data class PaymentResponse(
    val response_code: Int,
    val outcome_code: Int,
    val response_message: String,
    val txn_ref_no: String,
    val timestamp: String,
    val requester_ip: String,
    val timetaken: String
)