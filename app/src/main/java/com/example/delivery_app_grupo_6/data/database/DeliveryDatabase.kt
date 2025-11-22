package com.example.delivery_app_grupo_6.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.delivery_app_grupo_6.data.database.entities.UserEntity
import com.example.delivery_app_grupo_6.data.database.entities.RestaurantEntity
import com.example.delivery_app_grupo_6.data.database.entities.FoodEntity
import com.example.delivery_app_grupo_6.data.database.entities.OrderEntity
import com.example.delivery_app_grupo_6.data.database.entities.OrderItemEntity
import com.example.delivery_app_grupo_6.data.database.entities.CartItemEntity
import com.example.delivery_app_grupo_6.data.dao.UserDao
import com.example.delivery_app_grupo_6.data.dao.RestaurantDao
import com.example.delivery_app_grupo_6.data.dao.FoodDao
import com.example.delivery_app_grupo_6.data.dao.OrderDao
import com.example.delivery_app_grupo_6.data.dao.OrderItemDao
import com.example.delivery_app_grupo_6.data.dao.CartDao

@Database(
    entities = [
        UserEntity::class,
        RestaurantEntity::class,
        FoodEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun foodDao(): FoodDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: DeliveryDatabase? = null

        fun getInstance(context: Context): DeliveryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeliveryDatabase::class.java,
                    "delivery_database"
                )
                    .fallbackToDestructiveMigration() // Elimina y recrea la BD si hay migraciones
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}