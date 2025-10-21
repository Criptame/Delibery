package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivery_app_grupo_6.model.Product

@Composable
fun PantallaProductos(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    val sampleProducts = listOf( // ✅ "listOf" en minúscula
        Product(1, "Pizza", "Pizza margarita", 12.99, "", "Comida"),
        Product(2, "Hamburguesa", "Hamburguesa con queso", 8.99, "", "Comida"),
        Product(3, "Ensalada", "Ensalada César", 6.99, "", "Comida")
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Volver")
            }

            Text(
                "Nuestros Productos",
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(sampleProducts) { product ->
                    ProductItem(product = product)
                }
            }

            Button(
                onClick = onCartClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Ir al Carrito")
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = product.name)
            Text(text = product.description)
            Text(text = "$${product.price}")
        }
    }
}