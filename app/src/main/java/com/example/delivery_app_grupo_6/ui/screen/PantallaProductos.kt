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
import com.example.delivery_app_grupo_6.model.Product

@Composable
fun PantallaProductos(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    val productos = listOf(
        Product(1, "Pizza Margarita", "Pizza clásica con queso y tomate", 12.99, "", "Comida"),
        Product(2, "Hamburguesa", "Hamburguesa con queso y papas", 8.99, "", "Comida"),
        Product(3, "Ensalada César", "Ensalada fresca con pollo", 6.99, "", "Comida"),
        Product(4, "Refresco", "Bebida 500ml", 2.50, "", "Bebida"),
        Product(5, "Helado", "Postre de vainilla", 4.99, "", "Postre")
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
                Text("← Volver al Inicio")
            }

            Text(
                text = "🍔 Nuestro Menú",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(productos) { producto ->
                    ProductoItem(producto = producto)
                }
            }

            Button(
                onClick = onCartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Ver Carrito")
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = producto.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = producto.description,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Precio:")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$${"%.2f".format(producto.price)}",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { /* Agregar al carrito */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Agregar al Carrito")
            }
        }
    }
}