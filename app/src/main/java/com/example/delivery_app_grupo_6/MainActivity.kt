package com.example.delivery_app_grupo_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivery_app_grupo_6.navigation.MainApp
import com.example.delivery_app_grupo_6.ui.theme.Delivery_app_Grupo_6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeliveryApp()
        }
    }
}

@Composable
fun DeliveryApp() {
    Delivery_app_Grupo_6Theme {
        // Inicializar ViewModels aquí
        val productViewModel = viewModel<com.example.delivery_app_grupo_6.viewmodel.ProductViewModel>()
        val cartViewModel = viewModel<com.example.delivery_app_grupo_6.viewmodel.CartViewModel>()
        val userViewModel = viewModel<com.example.delivery_app_grupo_6.viewmodel.UserViewModel>()

        MainApp(
            productViewModel = productViewModel,
            cartViewModel = cartViewModel,
            userViewModel = userViewModel
        )
    }
}