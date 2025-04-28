package com.example.onebancrestaurant.data.api.getItemById

import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetItemByIdClient {
    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://uat.onebanc.ai")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: GetItemByIdService = retrofit.create(GetItemByIdService::class.java)
}