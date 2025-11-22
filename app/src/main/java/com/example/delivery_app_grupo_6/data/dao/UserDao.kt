package com.example.delivery_app_grupo_6.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.delivery_app_grupo_6.data.database.entities.UserEntity

@Dao
interface UserDao {

    // Crear usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    // Obtener usuario por ID
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): Flow<UserEntity?>

    // Obtener usuario por email
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    // Actualizar usuario
    @Update
    suspend fun updateUser(user: UserEntity)

    // Eliminar usuario
    @Delete
    suspend fun deleteUser(user: UserEntity)

    // Verificar si existe un usuario por email
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun userExists(email: String): Boolean

    // Obtener todos los usuarios (útil para admin)
    @Query("SELECT * FROM users ORDER BY name")
    fun getAllUsers(): Flow<List<UserEntity>>
}