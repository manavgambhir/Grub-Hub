package com.example.onebancrestaurant.data.repos

import com.example.onebancrestaurant.data.api.getItemById.GetItemByIdClient
import com.example.onebancrestaurant.data.models.GetItemByIdRequest

class GetItemByIdRepository {
    private val api = GetItemByIdClient.api
    suspend fun getItemById(request: GetItemByIdRequest) = api.getItemById(request)
}