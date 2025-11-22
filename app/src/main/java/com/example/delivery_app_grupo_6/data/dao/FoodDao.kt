package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.FoodEntity

@Dao
interface FoodDao {

    // Insertar comida
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity): Long

    // Insertar múltiples comidas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFoods(foods: List<FoodEntity>)

    // Obtener comidas por restaurante
    @Query("SELECT * FROM foods WHERE restaurant_id = :restaurantId AND is_available = 1 ORDER BY category, name")
    fun getFoodsByRestaurant(restaurantId: Long): Flow<List<FoodEntity>>

    // Obtener comida por ID
    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFoodById(foodId: Long): FoodEntity?

    // Buscar comidas por nombre
    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%' AND is_available = 1")
    fun searchFoods(query: String): Flow<List<FoodEntity>>

    // Obtener comidas por categoría
    @Query("SELECT * FROM foods WHERE category = :category AND is_available = 1 ORDER BY name")
    fun getFoodsByCategory(category: String): Flow<List<FoodEntity>>

    // Obtener categorías de comida por restaurante
    @Query("SELECT DISTINCT category FROM foods WHERE restaurant_id = :restaurantId AND is_available = 1")
    fun getFoodCategoriesByRestaurant(restaurantId: Long): Flow<List<String>>

    // Obtener comidas populares (podrías agregar un campo popularity después)
    @Query("SELECT * FROM foods WHERE is_available = 1 ORDER BY name LIMIT 20")
    fun getPopularFoods(): Flow<List<FoodEntity>>

    // Actualizar comida
    @Update
    suspend fun updateFood(food: FoodEntity)
}