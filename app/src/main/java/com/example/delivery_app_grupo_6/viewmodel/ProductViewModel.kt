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
            // PRODUCTOS REALES - sin URLs de ejemplo
            val realProducts = listOf(
                Product(
                    id = 1,
                    name = "Pizza Margarita",
                    description = "Pizza clásica con queso mozzarella y tomate fresco",
                    price = 12.99,
                    imageUrl = "",
                    category = "Pizzas"
                ),
                Product(
                    id = 2,
                    name = "Hamburguesa Clásica",
                    description = "Carne de res, lechuga, tomate y queso cheddar",
                    price = 8.99,
                    imageUrl = "",
                    category = "Hamburguesas"
                ),
                Product(
                    id = 3,
                    name = "Ensalada César",
                    description = "Lechuga romana, pollo a la parrilla y aderezo césar",
                    price = 6.99,
                    imageUrl = "",
                    category = "Ensaladas"
                ),
                Product(
                    id = 4,
                    name = "Refresco",
                    description = "Bebida refrescante 500ml",
                    price = 2.50,
                    imageUrl = "",
                    category = "Bebidas"
                ),
                Product(
                    id = 5,
                    name = "Helado de Vainilla",
                    description = "Postre cremoso de vainilla con topping",
                    price = 4.99,
                    imageUrl = "",
                    category = "Postres"
                )
            )
            _products.value = realProducts
            _filteredProducts.value = realProducts
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