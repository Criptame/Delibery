package com.example.delivery_app_grupo_6.componentes

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.delivery_app_grupp_6.R

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
        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = currentRoute == "home",
            onClick = {
                navigateToHome()
                closeDrawer()
            }
        )
        NavigationDrawerItem(
            label = { Text("Productos") },
            selected = currentRoute == "products",
            onClick = {
                navigateToProducts()
                closeDrawer()
            }
        )
        NavigationDrawerItem(
            label = { Text("Carrito") },
            selected = currentRoute == "cart",
            onClick = {
                navigateToCart()
                closeDrawer()
            }
        )
    }
}