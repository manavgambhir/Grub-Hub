package com.example.onebancrestaurant.data.repos

import android.util.Log
import com.example.onebancrestaurant.data.api.getItemByFilter.GetItemByFilterClient
import com.example.onebancrestaurant.data.models.GetItemByFilterRequest
import com.example.onebancrestaurant.data.models.GetItemByFilterResponse

class GetItemByFilterRepository {
    private val api = GetItemByFilterClient.api
    suspend fun getItemByFilter(request: GetItemByFilterRequest) = api.getItemByFilter(request)
}
