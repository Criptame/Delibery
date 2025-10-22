package com.example.delivery_app_grupo_6.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app_grupo_6.ui.screen.PantallaCarrito
import com.example.delivery_app_grupo_6.ui.screen.PantallaInicio
import com.example.delivery_app_grupo_6.ui.screen.PantallaProductos
import com.example.delivery_app_grupo_6.ui.screen.PantallaCrearPerfil

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable(route = "inicio") {
            PantallaInicio(
                onProductsClick = { navController.navigate("productos") },
                onCartClick = { navController.navigate("carrito") },
                onProfileClick = { navController.navigate("crearPerfil") }
            )
        }

        composable(route = "productos") {
            PantallaProductos(
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate("carrito") }
            )
        }

        composable(route = "carrito") {
            PantallaCarrito(
                onBackClick = { navController.popBackStack() },
                onOrderClick = {
                    // Aquí puedes procesar el pedido
                    println("Pedido confirmado!")
                }
            )
        }

        composable(route = "crearPerfil") {
            PantallaCrearPerfil(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { user ->
                    // Aquí guardas el usuario
                    println("Usuario guardado: $user")
                    navController.popBackStack()
                }
            )
        }
    }
}