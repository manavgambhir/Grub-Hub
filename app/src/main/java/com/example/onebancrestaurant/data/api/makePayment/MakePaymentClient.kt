package com.example.onebancrestaurant.data.api.makePayment

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MakePaymentClient {
    private val gson:Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://uat.onebanc.ai")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: MakePaymentService = retrofit.create(MakePaymentService::class.java)
}