package com.example.delivery_app_grupo_6.data.model

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("userId") val userId: Long,
    @SerializedName("total") val total: Double,
    @SerializedName("status") val status: String = "PENDING",
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("items") val items: List<OrderItem> = emptyList()
)

data class OrderItem(
    @SerializedName("productId") val productId: Long,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Double
)