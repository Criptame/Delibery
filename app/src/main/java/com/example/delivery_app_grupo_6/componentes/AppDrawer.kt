package com.example.delivery_app_grupo_6.componentss

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToCart: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet {
        // Item para Inicio
        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = currentRoute == "home",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            modifier = Modifier
        )

        // Item para Productos
        NavigationDrawerItem(
            label = { Text("Productos") },
            selected = currentRoute == "products",
            onClick = {
                navigateToProducts()
                closeDrawer()
            },
            modifier = Modifier
        )

        // Item para Carrito
        NavigationDrawerItem(
            label = { Text("Carrito") },
            selected = currentRoute == "cart",
            onClick = {
                navigateToCart()
                closeDrawer()
            },
            modifier = Modifier
        )
    }
}
