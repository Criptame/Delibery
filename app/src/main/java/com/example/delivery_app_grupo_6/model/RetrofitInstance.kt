package com.example.delivery_app_grupo_6.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton que proporciona la instancia configurada de Retrofit
 *
 * Siguiendo el patrón Singleton, nos aseguramos de tener una única instancia
 * de Retrofit en toda la aplicación
 */
object RetrofitInstance {

    // URL base de la API JSONPlaceholder para pruebas
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // Instancia lazy de Retrofit - se crea solo cuando se necesita por primera vez
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia lazy del ApiService - se crea usando la instancia de Retrofit
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}