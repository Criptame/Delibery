package com.example.delivery_app_grupo_6.repository

import android.util.Log
import com.example.delivery_app_grupo_6.data.RetrofitInstance
import com.example.delivery_app_grupo_6.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository @Inject constructor() {

    private val tag = "PostRepository"

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