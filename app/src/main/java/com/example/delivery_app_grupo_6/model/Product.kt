package com.example.delivery_app_grupo_6.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
)