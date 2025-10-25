package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.delivery_app_grupo_6.model.User

@Composable
fun PantallaInicio(
    onProductsClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    user: User?,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🍕 App Delivery",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (user != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "👋 ¡Bienvenido, ${user.name}!",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text("📧 ${user.email}")
                    Text("📱 ${user.phone}")
                    Text("🏠 ${user.address}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onProductsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("🍴 Ver Menú Completo")
        }

        Button(
            onClick = onCartClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("🛒 Ver Carrito")
        }

        Button(
            onClick = onProfileClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(if (user != null) "👤 Editar Perfil" else "👤 Crear Perfil")
        }
    }
}