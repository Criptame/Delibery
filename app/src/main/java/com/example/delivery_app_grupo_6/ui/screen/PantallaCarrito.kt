package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.delivery_app_grupo_6.viewmodel.CartViewModel

@Composable
fun PantallaCarrito(
    onBackClick: () -> Unit,
    onOrderClick: () -> Unit,
    cartViewModel: CartViewModel,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val subtotal = cartViewModel.getTotalPrice()
    val envio = 2.50
    val total = subtotal + envio

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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

            if (cartItems.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tu carrito está vacío",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Agrega productos desde el menú",
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { cartItem ->
                        ItemCarrito(
                            item = cartItem,
                            onIncrease = {
                                cartViewModel.updateQuantity(cartItem, cartItem.quantity + 1)
                            },
                            onDecrease = {
                                cartViewModel.updateQuantity(cartItem, cartItem.quantity - 1)
                            },
                            onRemove = { cartViewModel.removeFromCart(cartItem) }
                        )
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
                            Text("✅ Confirmar Pedido")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCarrito(
    item: com.example.delivery_app_grupo_6.model.CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Controles de cantidad
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Disminuir"
                        )
                    }

                    Text(
                        text = "${item.quantity}",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar"
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$${"%.2f".format(item.product.price * item.quantity)}",
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onRemove
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }
        }
    }
}