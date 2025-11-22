package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.CartItemEntity

@Dao
interface CartDao {

    // Agregar item al carrito
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItemEntity): Long

    // Obtener items del carrito de un usuario
    @Query("SELECT * FROM cart_items WHERE user_id = :userId ORDER BY added_at DESC")
    fun getCartItems(userId: Long): Flow<List<CartItemEntity>>

    // Obtener items del carrito con detalles de comida
    @Query("""
        SELECT ci.*, f.name as food_name, f.price as food_price, 
               f.image_url as food_image, f.restaurant_id as restaurant_id
        FROM cart_items ci
        INNER JOIN foods f ON ci.food_id = f.id
        WHERE ci.user_id = :userId
        ORDER BY ci.added_at DESC
    """)
    fun getCartItemsWithDetails(userId: Long): Flow<List<CartItemWithDetails>>

    // Actualizar cantidad de un item
    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :cartItemId")
    suspend fun updateQuantity(cartItemId: Long, quantity: Int)

    // Eliminar item del carrito
    @Delete
    suspend fun removeFromCart(cartItem: CartItemEntity)

    // Eliminar todos los items del carrito de un usuario
    @Query("DELETE FROM cart_items WHERE user_id = :userId")
    suspend fun clearCart(userId: Long)

    // Obtener el total de items en el carrito
    @Query("SELECT SUM(quantity) FROM cart_items WHERE user_id = :userId")
    fun getCartItemsCount(userId: Long): Flow<Int>

    // Verificar si un item ya está en el carrito
    @Query("SELECT * FROM cart_items WHERE user_id = :userId AND food_id = :foodId")
    suspend fun getCartItem(userId: Long, foodId: Long): CartItemEntity?

    // Data class para el resultado de la consulta con detalles
    data class CartItemWithDetails(
        val id: Long,
        val userId: Long,
        val foodId: Long,
        val quantity: Int,
        val specialInstructions: String?,
        val addedAt: Long,
        val foodName: String,
        val foodPrice: Double,
        val foodImage: String?,
        val restaurantId: Long
    )
}