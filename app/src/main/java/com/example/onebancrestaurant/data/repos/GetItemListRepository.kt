package com.example.onebancrestaurant.data.repos

import com.example.onebancrestaurant.data.api.getItemList.GetItemListService
import com.example.onebancrestaurant.data.models.GetItemListRequest
import com.example.onebancrestaurant.data.models.GetItemListResponse

class GetItemListRepository(private val service: GetItemListService) {
    suspend fun fetchItemList(): GetItemListResponse? {
        return try {
            val response = service.getItemList(request = GetItemListRequest())
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
