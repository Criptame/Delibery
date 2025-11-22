package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.RestaurantEntity

@Dao
interface RestaurantDao {

    // Insertar restaurante
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: RestaurantEntity): Long

    // Insertar múltiples restaurantes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurants: List<RestaurantEntity>)

    // Obtener todos los restaurantes abiertos
    @Query("SELECT * FROM restaurants WHERE is_open = 1 ORDER BY rating DESC")
    fun getAllRestaurants(): Flow<List<RestaurantEntity>>

    // Obtener restaurante por ID
    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    fun getRestaurantById(restaurantId: Long): Flow<RestaurantEntity?>

    // Buscar restaurantes por nombre
    @Query("SELECT * FROM restaurants WHERE name LIKE '%' || :query || '%' AND is_open = 1")
    fun searchRestaurants(query: String): Flow<List<RestaurantEntity>>

    // Obtener restaurantes por categoría
    @Query("SELECT * FROM restaurants WHERE category = :category AND is_open = 1 ORDER BY rating DESC")
    fun getRestaurantsByCategory(category: String): Flow<List<RestaurantEntity>>

    // Obtener categorías únicas
    @Query("SELECT DISTINCT category FROM restaurants WHERE is_open = 1")
    fun getCategories(): Flow<List<String>>

    // Actualizar restaurante
    @Update
    suspend fun updateRestaurant(restaurant: RestaurantEntity)

    // Obtener restaurantes favoritos (podrías agregar un campo is_favorite después)
    @Query("SELECT * FROM restaurants WHERE is_open = 1 ORDER BY rating DESC LIMIT 10")
    fun getFeaturedRestaurants(): Flow<List<RestaurantEntity>>
}