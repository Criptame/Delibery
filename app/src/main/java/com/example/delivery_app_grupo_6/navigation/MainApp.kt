package com.example.delivery_app_grupo_6.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app_grupo_6.componentss.AppDrawer
import com.example.delivery_app_grupo_6.componentss.AppTopBar
import com.example.delivery_app_grupo_6.viewmodel.CartViewModel
import com.example.delivery_app_grupo_6.viewmodel.ProductViewModel
import com.example.delivery_app_grupo_6.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun MainApp(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "inicio"

    var drawerTitle by remember { mutableStateOf("Delivery App") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigateToHome = {
                    navController.navigate("inicio") {
                        popUpTo("inicio") { inclusive = true }
                    }
                    drawerTitle = "Inicio"
                },
                navigateToProducts = {
                    navController.navigate("productos")
                    drawerTitle = "Productos"
                },
                navigateToCart = {
                    navController.navigate("carrito")
                    drawerTitle = "Carrito"
                },
                navigateToProfile = {
                    navController.navigate("crearPerfil")
                    drawerTitle = "Perfil"
                },
                closeDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        androidx.compose.material3.Scaffold(
            topBar = {
                AppTopBar(
                    title = drawerTitle,
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    },
                    cartItemCount = cartViewModel.getItemCount()
                )
            }
        ) { paddingValues ->
            AppNavigation(
                navController = navController,
                paddingValues = paddingValues,
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                userViewModel = userViewModel,
                onTitleChange = { newTitle -> drawerTitle = newTitle }
            )
        }
    }
}