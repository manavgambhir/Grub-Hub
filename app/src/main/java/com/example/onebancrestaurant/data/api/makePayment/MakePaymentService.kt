package com.example.onebancrestaurant.data.api.makePayment

import com.example.onebancrestaurant.data.models.PaymentRequest
import com.example.onebancrestaurant.data.models.PaymentResponse
import retrofit2.Response
import retrofit2.http.*

interface MakePaymentService {
    @Headers(
        "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
        "X-Forward-Proxy-Action: get_item_list",
        "Content-Type: application/json"
    )
    @POST("/emulator/interview/make_payment")
    suspend fun makePayment(
        @Body request: PaymentRequest
    ): Response<PaymentResponse>
}