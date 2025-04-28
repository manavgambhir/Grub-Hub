package com.example.onebancrestaurant.data.api.getItemList

import com.example.onebancrestaurant.data.models.GetItemListRequest
import com.example.onebancrestaurant.data.models.GetItemListResponse
import retrofit2.Response
import retrofit2.http.*

interface GetItemListService {
    @Headers(
        "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
        "X-Forward-Proxy-Action: get_item_list",
        "Content-Type: application/json"
    )
    @POST("/emulator/interview/get_item_list")
    suspend fun getItemList(
        @Body request: GetItemListRequest
    ): Response<GetItemListResponse>
}