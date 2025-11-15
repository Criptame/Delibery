package com.example.delivery_app_grupo_6.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para representar un Post de la API JSONPlaceholder
 *
 * Esta clase será utilizada para mapear la respuesta JSON de la API
 * a objetos Kotlin mediante Gson/Retrofit
 */
data class Post(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
) {
    /**
     * Función de utilidad para mostrar información resumida
     */
    fun getSummary(): String {
        return "Post #$id - ${title.take(30)}..."
    }
}