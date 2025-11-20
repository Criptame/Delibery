package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    // StateFlows para la UI
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Función para cargar posts (simulada por ahora)
    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Simular llamada a API - reemplaza con tu lógica real
                val simulatedPosts = listOf(
                    Post(1, 1, "Primer Post", "Este es el contenido del primer post de ejemplo."),
                    Post(2, 1, "Segundo Post", "Este es el contenido del segundo post con más detalles."),
                    Post(3, 2, "Tercer Post", "Contenido interesante del tercer post de la aplicación.")
                )

                _posts.value = simulatedPosts
            } catch (e: Exception) {
                _error.value = "Error al cargar posts: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función para limpiar errores
    fun clearError() {
        _error.value = null
    }

    // Cargar posts automáticamente al inicializar
    init {
        refresh()
    }
}

// Modelo de datos para Post
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)