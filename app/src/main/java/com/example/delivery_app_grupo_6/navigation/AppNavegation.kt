package com.example.delivery_app_grupo_6.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app_grupo_6.ui.screen.PantallaCarrito
import com.example.delivery_app_grupo_6.ui.screen.PantallaInicio
import com.example.delivery_app_grupo_6.ui.screen.PantallaProductos

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            PantallaInicio( // ✅ Nombre corregido
                onProductsClick = { navController.navigate("products") },
                onCartClick = { navController.navigate("cart") }
            )
        }
        composable("products") {
            PantallaProductos(
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate("cart") }
            )
        }
        composable("cart") {
            PantallaCarrito(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}