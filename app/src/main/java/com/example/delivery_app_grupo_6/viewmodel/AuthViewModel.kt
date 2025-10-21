package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var isLoggedIn: Boolean = false
        private set

    fun login(email: String, password: String): Boolean {
        // Simulación de login
        isLoggedIn = email.isNotEmpty() && password.isNotEmpty()
        return isLoggedIn
    }

    fun logout() {
        isLoggedIn = false
    }
}

