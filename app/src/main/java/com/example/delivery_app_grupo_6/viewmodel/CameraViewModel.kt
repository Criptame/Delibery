package com.example.delivery_app_grupo_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    private val _qrResult = MutableStateFlow<String?>(null)
    val qrResult: StateFlow<String?> = _qrResult.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    fun setQRResult(result: String) {
        viewModelScope.launch {
            _qrResult.value = result
        }
    }

    fun startScanning() {
        viewModelScope.launch {
            _isScanning.value = true
            _qrResult.value = null
        }
    }

    fun stopScanning() {
        viewModelScope.launch {
            _isScanning.value = false
        }
    }

    fun clearResult() {
        viewModelScope.launch {
            _qrResult.value = null
        }
    }

    // Función simplificada para procesar QR
    fun processQRCode(qrCode: String): String {
        return when {
            qrCode.contains("DESCUENTO", ignoreCase = true) -> "DISCOUNT"
            qrCode.contains("PROMO", ignoreCase = true) -> "PROMOTION"
            qrCode.matches(Regex("\\d+")) -> "PRODUCT_ID"
            qrCode.startsWith("http") -> "URL"
            else -> "UNKNOWN"
        }
    }
}