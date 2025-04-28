package com.example.onebancrestaurant.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.onebancrestaurant.data.models.*
import com.example.onebancrestaurant.data.repos.GetItemByFilterRepository
import kotlinx.coroutines.launch

class GetItemByFilterViewModel : ViewModel() {
    private val repository = GetItemByFilterRepository()
    private val _itemDetails = MutableLiveData<GetItemByFilterResponse>()
    val itemDetails: LiveData<GetItemByFilterResponse> = _itemDetails

    fun fetchItemByFilter(
        cuisineType: List<String>?,
        minPrice: Double?,
        maxPrice: Double?,
        minRating: Double?
    ) {
        val request = GetItemByFilterRequest(
            cuisineType = cuisineType,
            priceRange = if (minPrice != null && maxPrice != null) PriceRange(minPrice, maxPrice) else null,
            minRating = minRating
        )

        viewModelScope.launch {
            try {
                val response = repository.getItemByFilter(request)
                _itemDetails.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}