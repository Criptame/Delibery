package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app_grupo_6.data.model.User
import com.example.delivery_app_grupo_6.data.model.LoginRequest
import com.example.delivery_app_grupo_6.data.model.LoginResponse
import com.example.delivery_app_grupo_6.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Estados del usuario
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Estados de autenticación
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    // Estados de carga y errores
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // 🔐 FUNCIONES DE AUTENTICACIÓN

    fun register(user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = userRepository.register(user)
                if (response.isSuccessful && response.body() != null) {
                    _currentUser.value = response.body()
                    _isAuthenticated.value = true
                } else {
                    _errorMessage.value = "Error en el registro: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = userRepository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    _currentUser.value = loginResponse.user
                    _isAuthenticated.value = true
                    // Aquí podrías guardar el token en DataStore o SharedPreferences
                } else {
                    _errorMessage.value = "Credenciales incorrectas"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _currentUser.value = null
            _isAuthenticated.value = false
            _errorMessage.value = null
            // Aquí podrías limpiar el token almacenado
        }
    }

    // 👤 FUNCIONES DE GESTIÓN DE USUARIO

    fun updateUser(user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val currentUserId = _currentUser.value?.id
                if (currentUserId != null) {
                    val response = userRepository.updateUser(currentUserId, user)
                    if (response.isSuccessful && response.body() != null) {
                        _currentUser.value = response.body()
                    } else {
                        _errorMessage.value = "Error al actualizar perfil"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserProfile(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = userRepository.getUserById(userId)
                if (response.isSuccessful && response.body() != null) {
                    _currentUser.value = response.body()
                } else {
                    _errorMessage.value = "Error al cargar perfil"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 🧹 FUNCIONES DE LIMPIEZA

    fun clearUser() {
        viewModelScope.launch {
            _currentUser.value = null
            _isAuthenticated.value = false
        }
    }

    fun clearError() {
        viewModelScope.launch {
            _errorMessage.value = null
        }
    }

    // 💾 FUNCIONES LOCALES (mantenemos compatibilidad)

    fun saveUserLocal(user: User) {
        viewModelScope.launch {
            _currentUser.value = user
            _isAuthenticated.value = true
        }
    }
}