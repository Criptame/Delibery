package com.example.delivery_app_grupo_6.componentss

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToProfile: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Text(
            text = "Delivery App",
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = currentRoute == "inicio",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("Productos") },
            selected = currentRoute == "productos",
            onClick = {
                navigateToProducts()
                closeDrawer()
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("Carrito") },
            selected = currentRoute == "carrito",
            onClick = {
                navigateToCart()
                closeDrawer()
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("Perfil") },
            selected = currentRoute == "crearPerfil",
            onClick = {
                navigateToProfile()
                closeDrawer()
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}