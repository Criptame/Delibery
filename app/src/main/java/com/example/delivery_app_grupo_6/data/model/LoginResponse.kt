// Crea este archivo en: data/model/LoginResponse.kt
package com.example.delivery_app_grupo_6.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: User
)