package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.data.model.Product
import com.example.delivery_app_grupo_6.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    // Estados de productos
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Estados de carga y errores
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Producto seleccionado
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    init {
        loadProductsFromApi()
    }

    // 📦 FUNCIONES PRINCIPALES - VERSIÓN SIMPLIFICADA

    fun loadProductsFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Versión simple - sin verificación compleja
                val productsFromApi = productRepository.getProducts()
                _products.value = productsFromApi
                _filteredProducts.value = productsFromApi
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
                // Fallback a productos locales
                loadLocalProducts()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val productsByCategory = productRepository.getProductsByCategory(category)
                _filteredProducts.value = productsByCategory
                _selectedCategory.value = category
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar categoría: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductById(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val product = productRepository.getProductById(id)
                _selectedProduct.value = product
            } catch (e: Exception) {
                _errorMessage.value = "Producto no encontrado: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 🔍 FUNCIONES DE FILTRADO Y BÚSQUEDA (LOCALES)

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
                            it.description?.contains(query, ignoreCase = true) == true
                }
            }
        }
    }

    fun clearFilters() {
        viewModelScope.launch {
            _selectedCategory.value = null
            _filteredProducts.value = _products.value
            _errorMessage.value = null
        }
    }

    // 🏠 FUNCIONES LOCALES (como fallback)
    private fun loadLocalProducts() {
        viewModelScope.launch {
            val localProducts = listOf(
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
            _products.value = localProducts
            _filteredProducts.value = localProducts
        }
    }

    // 🧹 FUNCIONES DE LIMPIEZA
    fun clearError() {
        viewModelScope.launch {
            _errorMessage.value = null
        }
    }

    fun clearSelectedProduct() {
        viewModelScope.launch {
            _selectedProduct.value = null
        }
    }
}