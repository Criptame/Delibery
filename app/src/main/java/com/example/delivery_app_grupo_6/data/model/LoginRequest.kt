// Crea este archivo en: data/model/LoginRequest.kt
package com.example.delivery_app_grupo_6.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)