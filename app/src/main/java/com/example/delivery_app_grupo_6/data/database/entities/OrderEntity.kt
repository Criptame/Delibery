package com.example.delivery_app_grupo_6.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RestaurantEntity::class,
            parentColumns = ["id"],
            childColumns = ["restaurant_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "restaurant_id")
    val restaurantId: Long,

    @ColumnInfo(name = "total_amount")
    val totalAmount: Double,

    @ColumnInfo(name = "status")
    val status: String = "pending", // pending, confirmed, preparing, delivered, cancelled

    @ColumnInfo(name = "delivery_address")
    val deliveryAddress: String,

    @ColumnInfo(name = "delivery_instructions")
    val deliveryInstructions: String? = null,

    @ColumnInfo(name = "payment_method")
    val paymentMethod: String = "card", // card, cash

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "estimated_delivery")
    val estimatedDelivery: Long? = null
)