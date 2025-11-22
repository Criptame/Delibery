package com.example.delivery_app_grupo_6.data.database

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.delivery_app_grupo_6.data.database.entities.UserEntity
import com.example.delivery_app_grupo_6.data.database.entities.RestaurantEntity
import com.example.delivery_app_grupo_6.data.database.entities.FoodEntity

class DatabaseInitializer(private val context: Context) {

    private val database = DeliveryDatabase.getInstance(context)
    private val userDao = database.userDao()
    private val restaurantDao = database.restaurantDao()
    private val foodDao = database.foodDao()

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            insertSampleData()
        }
    }

    private suspend fun insertSampleData() {
        // Verificar si ya existen datos - FORMA CORRECTA
        val restaurants = restaurantDao.getAllRestaurants().first()
        if (restaurants.isNotEmpty()) {
            return // Ya hay datos, no insertar de nuevo
        }

        // 1. Insertar usuario de ejemplo
        val sampleUser = UserEntity(
            name = "Usuario Demo",
            email = "demo@delivery.com",
            phone = "+1234567890",
            address = "Calle Principal 123, Ciudad"
        )
        val userId = userDao.insertUser(sampleUser)

        // 2. Insertar restaurantes de ejemplo
        val sampleRestaurants = listOf(
            RestaurantEntity(
                name = "Pizza Hut",
                category = "Pizza",
                imageUrl = "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=400",
                rating = 4.5f,
                deliveryTime = 30,
                minOrder = 15.0,
                deliveryFee = 2.99,
                description = "Las mejores pizzas de la ciudad con ingredientes frescos"
            ),
            RestaurantEntity(
                name = "McDonalds",
                category = "Hamburguesas",
                imageUrl = "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=400",
                rating = 4.2f,
                deliveryTime = 25,
                minOrder = 10.0,
                deliveryFee = 1.99,
                description = "Hamburguesas deliciosas y rápidas"
            ),
            RestaurantEntity(
                name = "Sushi Bar",
                category = "Sushi",
                imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400",
                rating = 4.7f,
                deliveryTime = 40,
                minOrder = 20.0,
                deliveryFee = 3.99,
                description = "Sushi fresco y auténtico preparado por chefs expertos"
            ),
            RestaurantEntity(
                name = "Burger King",
                category = "Hamburguesas",
                imageUrl = "https://images.unsplash.com/photo-1572802419224-296b0aeee0d9?w=400",
                rating = 4.3f,
                deliveryTime = 28,
                minOrder = 12.0,
                deliveryFee = 2.49,
                description = "Whopper y hamburguesas a la parrilla"
            ),
            RestaurantEntity(
                name = "Dominos Pizza",
                category = "Pizza",
                imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400",
                rating = 4.4f,
                deliveryTime = 35,
                minOrder = 18.0,
                deliveryFee = 2.79,
                description = "Pizza caliente entregada en minutos"
            )
        )

        val restaurantIds = mutableListOf<Long>()
        sampleRestaurants.forEach { restaurant ->
            val restaurantId = restaurantDao.insertRestaurant(restaurant)
            restaurantIds.add(restaurantId)
        }

        // 3. Insertar comidas de ejemplo
        val sampleFoods = listOf(
            // Pizza Hut
            FoodEntity(
                name = "Pizza Margarita",
                description = "Pizza clásica con tomate, mozzarella fresca y albahaca",
                price = 12.99,
                imageUrl = "https://images.unsplash.com/photo-1604068549290-dea0e4a305ca?w=400",
                category = "Pizza Clásica",
                restaurantId = restaurantIds[0],
                ingredients = "Salsa de tomate, Mozzarella, Albahaca fresca",
                preparationTime = 20
            ),
            FoodEntity(
                name = "Pizza Pepperoni",
                description = "Pizza con pepperoni picante y queso mozzarella",
                price = 14.99,
                imageUrl = "https://images.unsplash.com/photo-1627626775842-04d6ca0af0ae?w=400",
                category = "Pizza Especial",
                restaurantId = restaurantIds[0],
                ingredients = "Salsa de tomate, Mozzarella, Pepperoni",
                preparationTime = 25,
                spicyLevel = 2
            ),
            FoodEntity(
                name = "Pizza Hawaiana",
                description = "Pizza con jamón, piña y queso mozzarella",
                price = 13.99,
                imageUrl = "https://images.unsplash.com/photo-1595708684082-a173bb3a06c5?w=400",
                category = "Pizza Especial",
                restaurantId = restaurantIds[0],
                ingredients = "Salsa de tomate, Mozzarella, Jamón, Piña",
                preparationTime = 22
            ),

            // McDonalds
            FoodEntity(
                name = "Big Mac",
                description = "Hamburguesa con dos carnes, salsa especial, lechuga, queso, cebolla y pepinillos",
                price = 5.99,
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400",
                category = "Hamburguesas",
                restaurantId = restaurantIds[1],
                ingredients = "Pan, Carne 100% vacuna, Queso, Lechuga, Salsa especial",
                preparationTime = 8
            ),
            FoodEntity(
                name = "McNuggets (6 piezas)",
                description = "Crujientes nuggets de pollo",
                price = 4.49,
                imageUrl = "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400",
                category = "Acompañamientos",
                restaurantId = restaurantIds[1],
                ingredients = "Pollo, Harina, Especias",
                preparationTime = 5
            ),

            // Sushi Bar
            FoodEntity(
                name = "Roll California",
                description = "Roll con cangrejo, aguacate, pepino y sésamo por fuera",
                price = 8.99,
                imageUrl = "https://images.unsplash.com/photo-1553621042-f6e147245754?w=400",
                category = "Rolls Clásicos",
                restaurantId = restaurantIds[2],
                ingredients = "Arroz, Alga nori, Cangrejo, Aguacate, Pepino, Sésamo",
                preparationTime = 12
            ),
            FoodEntity(
                name = "Sashimi Mixto",
                description = "Selección de sashimi fresco: salmón, atún y lubina",
                price = 15.99,
                imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400",
                category = "Sashimi",
                restaurantId = restaurantIds[2],
                ingredients = "Salmón, Atún, Lubina, Wasabi, Jengibre",
                preparationTime = 10
            ),

            // Burger King
            FoodEntity(
                name = "Whopper",
                description = "La hamburguesa más famosa con carne a la parrilla",
                price = 6.49,
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400",
                category = "Hamburguesas",
                restaurantId = restaurantIds[3],
                ingredients = "Carne a la parrilla, Pan, Lechuga, Tomate, Mayonesa",
                preparationTime = 10
            ),

            // Dominos Pizza
            FoodEntity(
                name = "Pizza Cuatro Quesos",
                description = "Pizza con mozzarella, parmesano, gorgonzola y fontina",
                price = 16.99,
                imageUrl = "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=400",
                category = "Pizza Gourmet",
                restaurantId = restaurantIds[4],
                ingredients = "Mozzarella, Parmesano, Gorgonzola, Fontina",
                preparationTime = 28
            )
        )

        foodDao.insertAllFoods(sampleFoods)
    }
}