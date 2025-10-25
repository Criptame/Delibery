package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val sampleProducts = listOf(
                Product(1, "Pizza Margarita", "Pizza clásica con queso y tomate", 12.99, "https://example.com/pizza.jpg", "Comida"),
                Product(2, "Hamburguesa", "Hamburguesa con queso y papas", 8.99, "https://example.com/burger.jpg", "Comida"),
                Product(3, "Ensalada César", "Ensalada fresca con pollo", 6.99, "https://example.com/salad.jpg", "Comida"),
                Product(4, "Refresco", "Bebida 500ml", 2.50, "https://example.com/soda.jpg", "Bebida"),
                Product(5, "Helado", "Postre de vainilla", 4.99, "https://example.com/icecream.jpg", "Postre"),
                Product(6, "Café", "Café americano", 3.50, "https://example.com/coffee.jpg", "Bebida"),
                Product(7, "Tacos", "Tacos de carne con salsa", 10.99, "https://example.com/tacos.jpg", "Comida"),
                Product(8, "Pastel", "Pastel de chocolate", 5.99, "https://example.com/cake.jpg", "Postre")
            )
            _products.value = sampleProducts
            _filteredProducts.value = sampleProducts
        }
    }

    fun filterByCategory(category: String?) {
        viewModelScope.launch {
            _selectedCategory.value = category
            _filteredProducts.value = if (category == null) {
                _products.value
            } else {
                _products.value.filter { it.category == category }
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _filteredProducts.value = if (query.isEmpty()) {
                _products.value
            } else {
                _products.value.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
            }
        }
    }
}