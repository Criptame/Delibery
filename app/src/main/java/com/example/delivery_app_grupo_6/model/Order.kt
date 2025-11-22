package com.example.delivery_app_grupo_6.model

data class Order(
    val id: Int,
    val userId: Int,
    val products: List<Product>,
    val total: Double,
    val status: String
)