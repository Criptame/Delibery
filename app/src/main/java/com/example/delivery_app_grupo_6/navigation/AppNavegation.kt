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
        startDestination = "inicio"
    ) {
        composable("inicio") {
            PantallaInicio(
                onProductsClick = { navController.navigate("productos") },
                onCartClick = { navController.navigate("carrito") }
            )
        }

        composable("productos") {
            PantallaProductos(
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate("carrito") }
            )
        }

        composable("carrito") {
            PantallaCarrito(
                onBackClick = { navController.popBackStack() },
                onOrderClick = {
                    // Aquí puedes procesar el pedido
                    println("Pedido confirmado!")
                }
            )
        }
    }
}