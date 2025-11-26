package com.example.delivery_app_grupo_6.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.delivery_app_grupo_6.ui.screen.PantallaCarrito
import com.example.delivery_app_grupo_6.ui.screen.PantallaInicio
import com.example.delivery_app_grupo_6.ui.screen.PantallaProductos
import com.example.delivery_app_grupo_6.ui.screen.PantallaCrearPerfil
import com.example.delivery_app_grupo_6.ui.screen.PantallaCamaraConQR
import com.example.delivery_app_grupo_6.ui.screen.PantallaConfirmacionPedido
import com.example.delivery_app_grupo_6.viewmodel.CartViewModel
import com.example.delivery_app_grupo_6.viewmodel.ProductViewModel
import com.example.delivery_app_grupo_6.viewmodel.UserViewModel
import android.widget.Toast
import kotlin.random.Random

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    userViewModel: UserViewModel,
    onTitleChange: (String) -> Unit
) {
    val currentUser = userViewModel.currentUser.collectAsStateWithLifecycle().value

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable(route = "inicio") {
            onTitleChange("Inicio")
            PantallaInicio(
                onProductsClick = {
                    navController.navigate("productos")
                    onTitleChange("Productos")
                },
                onCartClick = {
                    navController.navigate("carrito")
                    onTitleChange("Carrito")
                },
                onProfileClick = {
                    navController.navigate("crearPerfil")
                    onTitleChange("Perfil")
                },
                onGeneraClick = {
                    navController.navigate("camara")
                    onTitleChange("Cámara y QR")
                },
                user = currentUser,
                paddingValues = paddingValues
            )
        }

        composable(route = "productos") {
            onTitleChange("Productos")
            PantallaProductos(
                onBackClick = { navController.popBackStack() },
                onCartClick = {
                    navController.navigate("carrito")
                    onTitleChange("Carrito")
                },
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                paddingValues = paddingValues
            )
        }

        composable(route = "carrito") {
            onTitleChange("Carrito")
            PantallaCarrito(
                onBackClick = { navController.popBackStack() },
                onOrderClick = {
                    navController.navigate("confirmacionPedido")
                },
                cartViewModel = cartViewModel,
                paddingValues = paddingValues
            )
        }

        composable(route = "crearPerfil") {
            onTitleChange("Crear Perfil")
            PantallaCrearPerfil(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { user ->
                    // CORRECCIÓN: Usar updateUser que existe en tu UserViewModel
                    userViewModel.updateUser(user)
                    navController.popBackStack()
                    onTitleChange("Inicio")

                    // Mostrar mensaje de éxito
                    Toast.makeText(
                        navController.context,
                        "Perfil actualizado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                user = currentUser,
                paddingValues = paddingValues
            )
        }

        composable(route = "camara") {
            onTitleChange("Cámara y QR")
            PantallaCamaraConQR(
                onBackClick = { navController.popBackStack() },
                onQRScannerClick = {
                    Toast.makeText(
                        navController.context,
                        "QR simulado: DESCUENTO10",
                        Toast.LENGTH_LONG
                    ).show()
                },
                paddingValues = paddingValues
            )
        }

        composable(route = "confirmacionPedido") {
            onTitleChange("Confirmación")

            // Obtener datos actuales del carrito y usuario
            val cartItems = cartViewModel.cartItems.collectAsStateWithLifecycle().value
            val currentUser = userViewModel.currentUser.collectAsStateWithLifecycle().value
            val totalPrice = cartViewModel.getTotalPrice() + 2.50 // subtotal + envío

            PantallaConfirmacionPedido(
                onBackToHome = {
                    // Limpiar carrito y volver al inicio
                    cartViewModel.clearCart()
                    navController.navigate("inicio") {
                        popUpTo("inicio") { inclusive = true }
                    }
                    onTitleChange("Inicio")
                },
                orderNumber = generateOrderNumber(),
                estimatedTime = generateEstimatedTime(),
                cartItems = cartItems,
                user = currentUser,
                totalPrice = totalPrice,
                paddingValues = paddingValues
            )
        }
    }
}

// Funciones auxiliares para generar datos del pedido
private fun generateOrderNumber(): String {
    return (1000..9999).random().toString()
}

private fun generateEstimatedTime(): String {
    val minutes = (25..45).random()
    return "$minutes-${minutes + 10} minutos"
}