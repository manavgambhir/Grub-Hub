package com.example.onebancrestaurant.data.repos

import com.example.onebancrestaurant.data.api.makePayment.MakePaymentClient
import com.example.onebancrestaurant.data.api.makePayment.MakePaymentService
import com.example.onebancrestaurant.data.models.PaymentRequest

class MakePaymentRepository(private val service: MakePaymentService) {
    private val api = MakePaymentClient.api
    suspend fun makePayment(request: PaymentRequest) = api.makePayment(request)
}