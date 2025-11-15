package com.example.delivery_app_grupo_6.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz que define los endpoints de la API REST
 *
 * Retrofit implementará automáticamente estas funciones
 * y se encargará de las llamadas HTTP
 */
interface ApiService {

    /**
     * Obtiene la lista completa de posts
     * GET https://jsonplaceholder.typicode.com/posts
     */
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    /**
     * Obtiene un post específico por su ID
     * GET https://jsonplaceholder.typicode.com/posts/{id}
     */
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

    /**
     * Obtiene posts de un usuario específico
     * GET https://jsonplaceholder.typicode.com/posts?userId={userId}
     */
    @GET("posts")
    suspend fun getPostsByUserId(@Query("userId") userId: Int): Response<List<Post>>

    // 🚗 PARA TU APP DE DELIVERY (EJEMPLOS FUTUROS):
    /*
    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>

    @GET("menu/{restaurantId}")
    suspend fun getMenu(@Path("restaurantId") restaurantId: Int): Response<List<MenuItem>>

    @POST("orders")
    suspend fun createOrder(@Body order: Order): Response<Order>
    */
}