package com.example.delivery_app_grupo_6.repository

import com.example.delivery_app_grupo_6.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class ProductRepository {
    open suspend fun getProducts(): List<Product> {
        // Implementación real iría aquí
        return emptyList()
    }

    open fun getProductsFlow(): Flow<List<Product>> = flow {
        emit(emptyList())
    }
}