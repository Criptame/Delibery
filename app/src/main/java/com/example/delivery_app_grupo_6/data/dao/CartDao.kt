package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.CartItemEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItemEntity): Long

    @Query("SELECT * FROM cart_items WHERE user_id = :userId ORDER BY added_at DESC")
    fun getCartItems(userId: Long): Flow<List<CartItemEntity>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT ci.*, f.name as food_name, f.price as food_price, 
               f.image_url as food_image, f.restaurant_id as restaurant_id
        FROM cart_items ci
        INNER JOIN foods f ON ci.food_id = f.id
        WHERE ci.user_id = :userId
        ORDER BY ci.added_at DESC
    """)
    fun getCartItemsWithDetails(userId: Long): Flow<List<CartItemWithDetails>>

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :cartItemId")
    suspend fun updateQuantity(cartItemId: Long, quantity: Int)

    @Delete
    suspend fun removeFromCart(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE user_id = :userId")
    suspend fun clearCart(userId: Long)

    @Query("SELECT SUM(quantity) FROM cart_items WHERE user_id = :userId")
    fun getCartItemsCount(userId: Long): Flow<Int>

    @Query("SELECT * FROM cart_items WHERE user_id = :userId AND food_id = :foodId")
    suspend fun getCartItem(userId: Long, foodId: Long): CartItemEntity?

    data class CartItemWithDetails(
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "user_id") val userId: Long,
        @ColumnInfo(name = "food_id") val foodId: Long,
        @ColumnInfo(name = "quantity") val quantity: Int,
        @ColumnInfo(name = "special_instructions") val specialInstructions: String?,
        @ColumnInfo(name = "added_at") val addedAt: Long,
        @ColumnInfo(name = "food_name") val foodName: String,
        @ColumnInfo(name = "food_price") val foodPrice: Double,
        @ColumnInfo(name = "food_image") val foodImage: String?,
        @ColumnInfo(name = "restaurant_id") val restaurantId: Long
    )
}