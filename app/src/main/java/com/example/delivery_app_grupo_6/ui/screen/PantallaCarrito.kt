package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delivery_app_grupo_6.model.CartItem
import com.example.delivery_app_grupo_6.model.Product

@Composable
fun PantallaCarrito(
    onBackClick: () -> Unit,
    onOrderClick: () -> Unit
) {
    val carrito = listOf(
        CartItem(Product(1, "Pizza Margarita", "Pizza clásica", 12.99, "", "Comida"), 2),
        CartItem(Product(2, "Hamburguesa", "Con queso y papas", 8.99, "", "Comida"), 1),
        CartItem(Product(4, "Refresco", "Bebida 500ml", 2.50, "", "Bebida"), 3)
    )

    val subtotal = carrito.sumOf { it.product.price * it.quantity }
    val envio = 2.50
    val total = subtotal + envio

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
                Text("← Volver")
            }

            Text(
                text = "🛒 Mi Carrito",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            if (carrito.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text("Tu carrito está vacío")
                    Text("Agrega productos desde el menú")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(carrito) { item ->
                        ItemCarrito(item = item)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Resumen del Pedido",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Subtotal:")
                            Spacer(modifier = Modifier.weight(1f))
                            Text("$${"%.2f".format(subtotal)}")
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Envío:")
                            Spacer(modifier = Modifier.weight(1f))
                            Text("$${"%.2f".format(envio)}")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Total:",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "$${"%.2f".format(total)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onOrderClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Confirmar Pedido")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCarrito(item: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.product.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(text = item.product.description)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cantidad: ${item.quantity}")
                Spacer(modifier = Modifier.weight(1f))
                Text("$${"%.2f".format(item.product.price * item.quantity)}")
            }
        }
    }
}