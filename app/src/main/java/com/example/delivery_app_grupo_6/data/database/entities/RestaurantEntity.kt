package com.example.delivery_app_grupo_6.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "rating")
    val rating: Float = 0.0f,

    @ColumnInfo(name = "delivery_time")
    val deliveryTime: Int = 30, // en minutos

    @ColumnInfo(name = "min_order")
    val minOrder: Double = 0.0,

    @ColumnInfo(name = "delivery_fee")
    val deliveryFee: Double = 2.99,

    @ColumnInfo(name = "is_open")
    val isOpen: Boolean = true,

    @ColumnInfo(name = "description")
    val description: String? = null
)