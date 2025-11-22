package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.OrderItemEntity

@Dao
interface OrderItemDao {

    // Insertar item de pedido
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItemEntity): Long

    // Insertar múltiples items de pedido
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrderItems(orderItems: List<OrderItemEntity>)

    // Obtener items de un pedido
    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    fun getOrderItemsByOrder(orderId: Long): Flow<List<OrderItemEntity>>

    // Obtener detalles completos del pedido con información de comida
    @Query("""
        SELECT oi.*, f.name as food_name, f.image_url as food_image 
        FROM order_items oi
        INNER JOIN foods f ON oi.food_id = f.id
        WHERE oi.order_id = :orderId
    """)
    fun getOrderItemsWithDetails(orderId: Long): Flow<List<OrderItemWithDetails>>

    // Data class para el resultado de la consulta con detalles
    data class OrderItemWithDetails(
        val id: Long,
        val orderId: Long,
        val foodId: Long,
        val quantity: Int,
        val unitPrice: Double,
        val specialInstructions: String?,
        val foodName: String,
        val foodImage: String?
    )
}