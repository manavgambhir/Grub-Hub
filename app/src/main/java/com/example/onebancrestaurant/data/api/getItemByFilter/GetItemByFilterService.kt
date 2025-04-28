package com.example.onebancrestaurant.data.api.getItemByFilter

import com.example.onebancrestaurant.data.models.*
import retrofit2.http.*

interface GetItemByFilterService {
    @Headers(
        "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
        "X-Forward-Proxy-Action: get_item_by_filter",
        "Content-Type: application/json"
    )
    @POST("/emulator/interview/get_item_by_filter")
    suspend fun getItemByFilter(
        @Body request: GetItemByFilterRequest
    ): GetItemByFilterResponse
}