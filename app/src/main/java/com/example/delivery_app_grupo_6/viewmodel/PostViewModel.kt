package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.model.Post
import com.example.delivery_app_grupo_6.model.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

/**
 * ViewModel que maneja el estado de la UI relacionado con los Posts
 *
 * Sigue el patrón MVVM y expone el estado mediante StateFlows
 * para que la UI pueda reaccionar a los cambios
 */
class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    // Estado privado - mutable
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedPost = MutableStateFlow<Post?>(null)
    val selectedPost: StateFlow<Post?> = _selectedPost.asStateFlow()

    init {
        Log.d("PostViewModel", "ViewModel inicializado - cargando posts...")
        fetchPosts()
    }

    /**
     * Obtiene todos los posts desde el repository
     */
    fun fetchPosts() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getPosts()
                if (result.isSuccess) {
                    _posts.value = result.getOrDefault(emptyList())
                    Log.d("PostViewModel", "Posts cargados: ${_posts.value.size} elementos")
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                    Log.e("PostViewModel", "Error al cargar posts: ${_error.value}")
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
                Log.e("PostViewModel", "Excepción al cargar posts", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Obtiene un post específico por ID
     */
    fun fetchPostById(id: Int) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getPostById(id)
                if (result.isSuccess) {
                    _selectedPost.value = result.getOrNull()
                    Log.d("PostViewModel", "Post $id cargado exitosamente")
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Error al cargar post"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Obtiene posts de un usuario específico
     */
    fun fetchPostsByUserId(userId: Int) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getPostsByUserId(userId)
                if (result.isSuccess) {
                    _posts.value = result.getOrDefault(emptyList())
                    Log.d("PostViewModel", "Posts del usuario $userId cargados: ${_posts.value.size} elementos")
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Error al cargar posts del usuario"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Limpia el mensaje de error
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Limpia el post seleccionado
     */
    fun clearSelectedPost() {
        _selectedPost.value = null
    }

    /**
     * Recarga los posts
     */
    fun refresh() {
        fetchPosts()
    }
}