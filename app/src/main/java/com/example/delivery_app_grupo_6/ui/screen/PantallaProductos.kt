package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.delivery_app_grupo_6.model.Product
import com.example.delivery_app_grupo_6.viewmodel.CartViewModel
import com.example.delivery_app_grupo_6.viewmodel.ProductViewModel

@Composable
fun PantallaProductos(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    val products = productViewModel.filteredProducts.collectAsStateWithLifecycle().value
    val allProducts = productViewModel.products.collectAsStateWithLifecycle().value
    val selectedCategory = productViewModel.selectedCategory.collectAsStateWithLifecycle().value

    // Cargar productos al iniciar
    LaunchedEffect(Unit) {
        if (allProducts.isEmpty()) {
            // Los productos se cargan automáticamente en el init del ViewModel
        }
    }

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
                Text("← Volver al Inicio")
            }

            Text(
                text = "Nuestro Menú",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Filtros de categoría
            val uniqueCategories = allProducts.map { it.category }.distinct()
            if (uniqueCategories.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { productViewModel.filterByCategory(null) },
                        label = { Text("Todos") }
                    )
                    uniqueCategories.forEach { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { productViewModel.filterByCategory(category) },
                            label = { Text(category) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (products.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No hay productos disponibles",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Intenta con otra categoría",
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(products) { product ->
                        ProductoItem(
                            producto = product,
                            onAddToCart = { cartViewModel.addToCart(product) }
                        )
                    }
                }
            }

            Button(
                onClick = onCartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Ver Carrito (${cartViewModel.getItemCount()})")
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Product,
    onAddToCart: () -> Unit
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
                Text("Categoría: ${producto.category}")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$${"%.2f".format(producto.price)}",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Agregar al Carrito")
            }
        }
    }
}