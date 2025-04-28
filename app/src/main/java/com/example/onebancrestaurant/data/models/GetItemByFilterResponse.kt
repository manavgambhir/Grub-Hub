package com.example.onebancrestaurant.data.models

data class GetItemByFilterResponse(
    val response_code: Int,
    val outcome_code: Int,
    val response_message: String,
    val cuisines: List<Cuisines>,
    val timestamp: String,
    val requester_ip: String,
    val timetaken: String
)

data class Cuisines(
    val cuisine_id: Int,
    val cuisine_name: String,
    val cuisine_image_url: String,
    val items: List<Item>
)

data class Item(
    val id: Int,
    val name: String,
    val image_url: String
)
