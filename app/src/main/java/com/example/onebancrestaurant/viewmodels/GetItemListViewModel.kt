package com.example.onebancrestaurant.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.onebancrestaurant.data.api.getItemList.GetItemListClient
import com.example.onebancrestaurant.data.models.GetItemListResponse
import com.example.onebancrestaurant.data.repos.GetItemListRepository
import kotlinx.coroutines.launch

class GetItemListViewModel : ViewModel() {
    private val repository = GetItemListRepository(GetItemListClient.api)
    private val _itemList = MutableLiveData<GetItemListResponse?>()
    val itemList: LiveData<GetItemListResponse?> = _itemList

    fun fetchItems() {
        viewModelScope.launch {
            val result = repository.fetchItemList()
            Log.d("Test123", result.toString())
            _itemList.postValue(result)
        }
    }
}