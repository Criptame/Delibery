package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.data.model.CartItem
import com.example.delivery_app_grupo_6.data.model.Order
import com.example.delivery_app_grupo_6.data.model.OrderItem
import com.example.delivery_app_grupo_6.data.model.Product
import com.example.delivery_app_grupo_6.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    // Estados del carrito
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Estados de carga y errores
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _orderSuccess = MutableStateFlow(false)
    val orderSuccess: StateFlow<Boolean> = _orderSuccess.asStateFlow()

    // 🛒 FUNCIONES DEL CARRITO (LOCALES)

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
                it.product.id == item.product.id
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
            _orderSuccess.value = false
            _errorMessage.value = null
        }
    }

    // 📦 FUNCIONES DE ORDEN (API)

    fun createOrder(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _orderSuccess.value = false

            try {
                if (_cartItems.value.isEmpty()) {
                    _errorMessage.value = "El carrito está vacío"
                    return@launch
                }

                // Convertir cartItems a orderItems
                val orderItems = _cartItems.value.map { cartItem ->
                    OrderItem(
                        productId = cartItem.product.id ?: 0,
                        quantity = cartItem.quantity,
                        price = cartItem.product.price
                    )
                }

                val order = Order(
                    userId = userId,
                    total = getTotalPrice(),
                    status = "PENDING",
                    items = orderItems
                )

                // Llamar al repository para crear la orden
                val createdOrder = orderRepository.createOrder(order)

                if (createdOrder != null) {
                    _orderSuccess.value = true
                    clearCart() // Limpiar carrito después de orden exitosa
                } else {
                    _errorMessage.value = "Error al crear la orden"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserOrders(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val orders = orderRepository.getUserOrders(userId)
                // Aquí podrías manejar las órdenes obtenidas si necesitas
                // _userOrders.value = orders
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar órdenes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 💰 FUNCIONES DE CÁLCULO

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }

    // 🧹 FUNCIONES DE LIMPIEZA

    fun clearError() {
        viewModelScope.launch {
            _errorMessage.value = null
        }
    }

    fun clearOrderSuccess() {
        viewModelScope.launch {
            _orderSuccess.value = false
        }
    }


}