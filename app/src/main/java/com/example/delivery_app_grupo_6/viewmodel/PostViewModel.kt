package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    // Aquí puedes agregar la lógica para obtener posts de tu API
    fun loadPosts() {
        viewModelScope.launch {
            // Tu lógica para cargar posts
        }
    }
}

// Modelo de datos para Post
data class Post(
    val id: Int,
    val title: String,
    val body: String
)