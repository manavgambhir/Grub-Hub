package com.example.onebancrestaurant.data.models

data class GetItemListRequest(
    var page: Int? = null,
    var count: Int? = null
){
    init {
        this.page = 1
        this.count = 10
    }
}
