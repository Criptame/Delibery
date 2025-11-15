package com.example.delivery_app_grupo_6.model

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository pattern - actúa como intermediario entre ViewModel y fuentes de datos
 *
 * Encapsula la lógica de acceso a datos y proporciona una API limpia al ViewModel
 */
class PostRepository {

    private val tag = "PostRepository"

    /**
     * Obtiene la lista de posts desde la API
     * @return Result con la lista de posts o error
     */
    suspend fun getPosts(): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            Log.d(tag, "Iniciando petición de posts...")

            val response = RetrofitInstance.apiService.getPosts()

            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                Log.d(tag, "Posts obtenidos exitosamente: ${posts.size} elementos")
                Result.success(posts)
            } else {
                val errorMessage = "Error HTTP: ${response.code()} - ${response.message()}"
                Log.e(tag, errorMessage)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e(tag, "Error al obtener posts: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Obtiene un post específico por su ID
     * @param id ID del post a obtener
     * @return Result con el post o error
     */
    suspend fun getPostById(id: Int): Result<Post> = withContext(Dispatchers.IO) {
        try {
            Log.d(tag, "Obteniendo post con ID: $id")

            val response = RetrofitInstance.apiService.getPostById(id)

            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    Log.d(tag, "Post $id obtenido exitosamente")
                    Result.success(post)
                } else {
                    Result.failure(Exception("Post no encontrado"))
                }
            } else {
                val errorMessage = "Error HTTP: ${response.code()} - ${response.message()}"
                Log.e(tag, errorMessage)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e(tag, "Error al obtener post $id: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Obtiene posts de un usuario específico
     * @param userId ID del usuario
     * @return Result con la lista de posts del usuario
     */
    suspend fun getPostsByUserId(userId: Int): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            Log.d(tag, "Obteniendo posts del usuario: $userId")

            val response = RetrofitInstance.apiService.getPostsByUserId(userId)

            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                Log.d(tag, "Posts del usuario $userId obtenidos: ${posts.size} elementos")
                Result.success(posts)
            } else {
                val errorMessage = "Error HTTP: ${response.code()} - ${response.message()}"
                Log.e(tag, errorMessage)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e(tag, "Error al obtener posts del usuario $userId: ${e.message}", e)
            Result.failure(e)
        }
    }
}