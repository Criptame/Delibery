package com.example.delivery_app_grupo_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivery_app_grupo_6.navigation.MainApp
import com.example.delivery_app_grupo_6.ui.theme.Delivery_app_Grupo_6Theme
import com.example.delivery_app_grupo_6.viewmodel.CartViewModel
import com.example.delivery_app_grupo_6.viewmodel.ProductViewModel
import com.example.delivery_app_grupo_6.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Delivery_app_Grupo_6Theme {
                val productViewModel: ProductViewModel = viewModel()
                val cartViewModel: CartViewModel = viewModel()
                val userViewModel: UserViewModel = viewModel()

                MainApp(
                    productViewModel = productViewModel,
                    cartViewModel = cartViewModel,
                    userViewModel = userViewModel
                )
            }
        }
    }
}