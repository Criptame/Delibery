package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun saveUser(user: User) {
        viewModelScope.launch {
            _currentUser.value = user
        }
    }

    fun clearUser() {
        viewModelScope.launch {
            _currentUser.value = null
        }
    }
}