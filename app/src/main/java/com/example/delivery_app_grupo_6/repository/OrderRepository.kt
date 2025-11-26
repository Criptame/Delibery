// repository/OrderRepository.kt
package com.example.delivery_app_grupo_6.repository

import com.example.delivery_app_grupo_6.data.RetrofitInstance
import com.example.delivery_app_grupo_6.data.model.Order
import javax.inject.Inject

class OrderRepository @Inject constructor() {

    private val apiService = RetrofitInstance.apiService

    suspend fun createOrder(order: Order): Order? {
        val response = apiService.createOrder(order)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getUserOrders(userId: Long): List<Order> {
        val response = apiService.getUserOrders(userId)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getOrderById(orderId: Long): Order? {
        val response = apiService.getOrderById(orderId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}