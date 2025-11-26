// En data/model/User.kt - asegúrate de que tenga esta estructura:
package com.example.delivery_app_grupo_6.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)