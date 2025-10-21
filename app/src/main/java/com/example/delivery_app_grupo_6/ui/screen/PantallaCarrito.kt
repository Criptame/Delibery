package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivery_app_grupp_6.model.CartItem
import com.example.delivery_app_grupp_6.model.Product

@Composable
fun PantallaCarrito(
    onBackClick: () -> Unit
) {
    val sampleCartItems = listOf(
        CartItem(Product(1, "Pizza", "Pizza margarita", 12.99, "", "Comida"), 2),
        CartItem(Product(2, "Hamburguesa", "Hamburguesa con queso", 8.99, "", "Comida"), 1)
    )

    val total = sampleCartItems.sumOf { it.product.price * it.quantity }

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
                "Tu Carrito",
                modifier = Modifier.padding(16.dp)
            )

            sampleCartItems.forEach { cartItem ->
                CartItemView(cartItem = cartItem)
            }

            Text(
                "Total: $$total",
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = { /* Procesar pedido */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Realizar Pedido")
            }
        }
    }
}

@Composable
fun CartItemView(cartItem: CartItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = cartItem.product.name)
            Text(text = "Cantidad: ${cartItem.quantity}")
            Text(text = "Precio: $${cartItem.product.price * cartItem.quantity}")
        }
    }
}