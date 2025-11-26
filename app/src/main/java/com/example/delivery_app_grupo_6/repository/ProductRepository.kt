// repository/ProductRepository.kt - VERSIÓN SIMPLIFICADA
package com.example.delivery_app_grupo_6.repository

import com.example.delivery_app_grupo_6.data.RetrofitInstance
import com.example.delivery_app_grupo_6.data.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor() {

    private val apiService = RetrofitInstance.apiService

    // Versión simple - sin Response wrapper
    suspend fun getProducts(): List<Product> {
        val response = apiService.getProducts()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getProductById(id: Long): Product? {
        val response = apiService.getProductById(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        val response = apiService.getProductsByCategory(category)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}