package com.example.onebancrestaurant.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.onebancrestaurant.CartItem

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<Map<String, CartItem>>(emptyMap())
    val cartItems: LiveData<Map<String, CartItem>> = _cartItems

    fun addItem(item: CartItem) {
//        Log.d("CartViewModel", "Adding item: $item")
        _cartItems.value = _cartItems.value?.toMutableMap()?.apply {
            val existing = get(item.id)
            if (existing != null) {
                put(item.id, existing.copy(quantity = existing.quantity + 1))
            } else {
                put(item.id, item.copy(quantity = 1))
            }
        }
//        Log.d("CartViewModel", "Updated cart items: ${_cartItems.value}")
    }

    fun incrementQuantity(itemId: String) {
        _cartItems.value = _cartItems.value?.let { currentCart ->
            val updatedCart = currentCart.toMutableMap()
            currentCart[itemId]?.let { item ->
                updatedCart[itemId] = item.copy(quantity = item.quantity + 1)
            }
            updatedCart.toMap()
        }
//        Log.d("CartViewModel", "Increment cart items: ${_cartItems.value}")
    }

    fun decrementQuantity(itemId: String) {
        _cartItems.value = _cartItems.value?.let { currentCart ->
            val updatedCart = currentCart.toMutableMap()
            currentCart[itemId]?.let { item ->
                if (item.quantity > 1) {
                    updatedCart[itemId] = item.copy(quantity = item.quantity - 1)
                } else {
                    updatedCart.remove(itemId)
                }
            }
            updatedCart.toMap()
        }
//        Log.d("CartViewModel", "Decrement cart items: ${_cartItems.value}")
    }

    fun removeItem(itemId: String) {
        _cartItems.value = _cartItems.value?.toMutableMap()?.apply {
            remove(itemId)
        }
    }

    fun fetchCartItems(): LiveData<Map<String, CartItem>> {
        return cartItems
    }
}