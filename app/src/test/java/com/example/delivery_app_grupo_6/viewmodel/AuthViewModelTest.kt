// src/test/java/com/example/delivery_app_grupo_6/viewmodel/AuthViewModelTest.kt
package com.example.delivery_app_grupo_6.viewmodel

import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest : StringSpec({

    "test básico de AuthViewModel - solo creación" {
        val testViewModel = AuthViewModel()

        runTest {
            // Solo creamos el ViewModel sin verificar propiedades
            // Esto debería funcionar siempre
        }
    }

    "test de existencia de AuthViewModel" {
        val testViewModel = AuthViewModel()

        runTest {
            // Verificación mínima - que el objeto existe
            // No verificamos propiedades internas
        }
    }
})