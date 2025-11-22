package com.example.delivery_app_grupo_6

import android.app.Application
import com.example.delivery_app_grupo_6.data.database.DeliveryDatabase
import com.example.delivery_app_grupo_6.data.database.DatabaseInitializer

class DeliveryApplication : Application() {

    // Instancia única de la base de datos
    val database: DeliveryDatabase by lazy {
        DeliveryDatabase.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()

        // Inicializar la base de datos con datos de ejemplo
        DatabaseInitializer(this).initialize()

        println("✅ DeliveryApplication iniciada - Base de datos lista")
    }
}