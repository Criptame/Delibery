package com.example.delivery_app_grupo_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.delivery_app_grupo_6.navigation.AppNavigation
import com.example.delivery_app_grupo_6.ui.theme.Delivery_app_grupo_6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Delivery_app_grupo_6Theme {
                AppNavigation()
            }
        }
    }
}