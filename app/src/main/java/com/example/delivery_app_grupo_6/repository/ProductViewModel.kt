package com.example.delivery_app_grupo_6.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.model.Product
import com.example.delivery_app_grupo_6.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ProductViewModel : ViewModel() {
    protected val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    open fun fetchProducts() {
        viewModelScope.launch {
            // Implementación real iría aquí
            _productList.value = emptyList()
        }
    }
}