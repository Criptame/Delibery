// Crea este archivo en: repository/UserRepository.kt
package com.example.delivery_app_grupo_6.repository

import com.example.delivery_app_grupo_6.data.RetrofitInstance
import com.example.delivery_app_grupo_6.data.model.LoginRequest
import com.example.delivery_app_grupo_6.data.model.LoginResponse
import com.example.delivery_app_grupo_6.data.model.User
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor() {

    private val apiService = RetrofitInstance.apiService

    suspend fun register(user: User): Response<User> {
        return apiService.register(user)
    }

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(email, password))
    }

    suspend fun getUserById(id: Long): Response<User> {
        return apiService.getUserById(id)
    }

    suspend fun updateUser(id: Long, user: User): Response<User> {
        return apiService.updateUser(id, user)
    }
}