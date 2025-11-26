package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.OrderItemEntity

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrderItems(orderItems: List<OrderItemEntity>)

    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    fun getOrderItemsByOrder(orderId: Long): Flow<List<OrderItemEntity>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT oi.*, f.name as food_name, f.image_url as food_image 
        FROM order_items oi
        INNER JOIN foods f ON oi.food_id = f.id
        WHERE oi.order_id = :orderId
    """)
    fun getOrderItemsWithDetails(orderId: Long): Flow<List<OrderItemWithDetails>>

    data class OrderItemWithDetails(
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "order_id") val orderId: Long,
        @ColumnInfo(name = "food_id") val foodId: Long,
        @ColumnInfo(name = "quantity") val quantity: Int,
        @ColumnInfo(name = "unit_price") val unitPrice: Double,
        @ColumnInfo(name = "special_instructions") val specialInstructions: String?,
        @ColumnInfo(name = "food_name") val foodName: String,
        @ColumnInfo(name = "food_image") val foodImage: String?
    )
}