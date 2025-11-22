package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.OrderEntity

@Dao
interface OrderDao {

    // Crear pedido
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    // Obtener pedido por ID
    @Query("SELECT * FROM orders WHERE id = :orderId")
    fun getOrderById(orderId: Long): Flow<OrderEntity?>

    // Obtener pedidos de un usuario
    @Query("SELECT * FROM orders WHERE user_id = :userId ORDER BY created_at DESC")
    fun getOrdersByUser(userId: Long): Flow<List<OrderEntity>>

    // Obtener pedidos por estado
    @Query("SELECT * FROM orders WHERE user_id = :userId AND status = :status ORDER BY created_at DESC")
    fun getOrdersByStatus(userId: Long, status: String): Flow<List<OrderEntity>>

    // Actualizar estado de pedido
    @Query("UPDATE orders SET status = :status WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: Long, status: String)

    // Actualizar pedido completo
    @Update
    suspend fun updateOrder(order: OrderEntity)

    // Obtener pedidos recientes (últimos 30 días)
    @Query("SELECT * FROM orders WHERE user_id = :userId AND created_at >= :sinceDate ORDER BY created_at DESC")
    fun getRecentOrders(userId: Long, sinceDate: Long): Flow<List<OrderEntity>>

    // Cancelar pedido
    @Query("UPDATE orders SET status = 'cancelled' WHERE id = :orderId AND status IN ('pending', 'confirmed')")
    suspend fun cancelOrder(orderId: Long): Int
}