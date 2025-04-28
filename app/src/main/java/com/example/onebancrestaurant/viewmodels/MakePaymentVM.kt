package com.example.onebancrestaurant.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.onebancrestaurant.CartItem
import com.example.onebancrestaurant.data.api.makePayment.MakePaymentClient
import com.example.onebancrestaurant.data.models.*
import com.example.onebancrestaurant.data.repos.*
import kotlinx.coroutines.launch

class MakePaymentVM: ViewModel() {
    private val repository = MakePaymentRepository(MakePaymentClient.api)

    private val _paymentResponse = MutableLiveData<PaymentResponse?>()
    val paymentResponse: LiveData<PaymentResponse?> = _paymentResponse

    fun makePayment(cartItems: Map<String, CartItem>) {
        viewModelScope.launch {
            try {
                val paymentRequest:PaymentRequest = createPaymentRequest(cartItems)

                val response = repository.makePayment(paymentRequest)
                Log.d("PaymentTest123",response.toString())

                if (response.isSuccessful) {
                    _paymentResponse.postValue(response.body())
                }
            } catch (e: Exception) {
                Log.d("NetworkError", "Network Error: ${e.message}")
            }
        }
    }

    private suspend fun createPaymentRequest(cartItems: Map<String, CartItem>): PaymentRequest {
        val cartItemsList = cartItems.values.map { item ->
            CartItemRequest(
                cuisine_id = getCuisineId(item.id.toInt()),
                item_id = item.id.toInt(),
                item_price = item.price,
                item_quantity = item.quantity
            )
        }

        return PaymentRequest(
            total_amount = cartItemsList.sumOf { it.item_price * it.item_quantity },
            total_items = cartItemsList.size,
            data = cartItemsList
        )
    }

    private suspend fun getCuisineId(itemId: Int): Int {
        return try {
            val response = GetItemByIdRepository().getItemById(GetItemByIdRequest(itemId))
//            Log.d("PaymentTest123",response.toString())
            if (response.outcome_code == 200) {
                response.cuisine_id.toInt()
            } else {
                Log.e("GetCuisineId", "Failed to get cuisine ID: ${response.response_message}")
                0
            }
        } catch (e: Exception) {
            Log.e("GetCuisineId", "Error fetching cuisine ID: ${e.message}")
            0
        }
    }
}