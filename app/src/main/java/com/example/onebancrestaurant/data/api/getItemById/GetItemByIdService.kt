package com.example.onebancrestaurant.data.api.getItemById

import com.example.onebancrestaurant.data.models.*
import retrofit2.http.*

interface GetItemByIdService {
    @Headers(
        "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
        "X-Forward-Proxy-Action: get_item_by_id",
        "Content-Type: application/json"
    )
    @POST("/emulator/interview/get_item_by_id")
    suspend fun getItemById(
        @Body request: GetItemByIdRequest
    ): GetItemByIdResponse
}