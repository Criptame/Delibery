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
import com.example.delivery_app_grupo_6.viewmodel.CartViewModel
import com.example.delivery_app_grupo_6.viewmodel.ProductViewModel
import com.example.delivery_app_grupo_6.viewmodel.UserViewModel
import android.widget.Toast

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
                onGeneraClick = { // CAMBIADO: onCameraClick → onGeneraClick
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
                    cartViewModel.clearCart()
                    navController.navigate("inicio") {
                        popUpTo("inicio") { inclusive = true }
                    }
                    onTitleChange("Inicio")
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
                    userViewModel.saveUser(user)
                    navController.popBackStack()
                    onTitleChange("Inicio")
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
    }
}