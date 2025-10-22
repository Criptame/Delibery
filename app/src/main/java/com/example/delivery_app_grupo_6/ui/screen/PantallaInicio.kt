package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaInicio(
    onProductsClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "App Delivery",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = onProductsClick,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Ver Menú")
            }

            Button(
                onClick = onCartClick,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Ver Carrito")
            }
        }
    }
}