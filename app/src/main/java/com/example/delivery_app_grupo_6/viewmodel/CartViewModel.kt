package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.model.CartItem
import com.example.delivery_app_grupo_6.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableList()
            val existingItem = currentItems.find { it.product.id == product.id }

            if (existingItem != null) {
                // Incrementar cantidad si ya existe
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                currentItems[currentItems.indexOf(existingItem)] = updatedItem
            } else {
                // Agregar nuevo item
                currentItems.add(CartItem(product, 1))
            }

            _cartItems.value = currentItems
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableList()
            currentItems.remove(item)
            _cartItems.value = currentItems
        }
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                removeFromCart(item)
                return@launch
            }

            val currentItems = _cartItems.value.toMutableList()
            val existingItemIndex = currentItems.indexOfFirst {
                it.product.id == item.product.id && it.quantity == item.quantity
            }

            if (existingItemIndex != -1) {
                val updatedItem = item.copy(quantity = newQuantity)
                currentItems[existingItemIndex] = updatedItem
                _cartItems.value = currentItems
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }
}