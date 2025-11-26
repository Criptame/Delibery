package com.example.delivery_app_grupo_6.data

import com.example.delivery_app_grupo_6.data.model.LoginRequest
import com.example.delivery_app_grupo_6.data.model.LoginResponse
import com.example.delivery_app_grupo_6.data.model.Order
import com.example.delivery_app_grupo_6.data.model.Post
import com.example.delivery_app_grupo_6.data.model.Product
import com.example.delivery_app_grupo_6.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // 🔐 AUTH ENDPOINTS
    @POST("auth/register")
    suspend fun register(@Body user: User): Response<User>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // 📦 PRODUCT ENDPOINTS
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<Product>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>

    // 🛒 ORDER ENDPOINTS
    @GET("orders/user/{userId}")
    suspend fun getUserOrders(@Path("userId") userId: Long): Response<List<Order>>

    @POST("orders")
    suspend fun createOrder(@Body order: Order): Response<Order>

    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: Long): Response<Order>

    // 👤 USER ENDPOINTS
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): Response<User>

    // 📝 POST ENDPOINTS (JSONPlaceholder API)
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

    @GET("posts")
    suspend fun getPostsByUserId(@Query("userId") userId: Int): Response<List<Post>>
}