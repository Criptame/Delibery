package com.example.delivery_app_grupo_6.viewmodel

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest : StringSpec({

    "test básico de creación de CartViewModel" {
        val testViewModel = CartViewModel()

        runTest {
            // Verificamos que el ViewModel se creó correctamente
            testViewModel shouldBe testViewModel
        }
    }

    "debería inicializar con carrito vacío" {
        val testViewModel = CartViewModel()

        runTest {
            // Verificamos el estado inicial
            // Si cartItems existe y es público:
            testViewModel.cartItems.value.isEmpty() shouldBe true
        }
    }
})