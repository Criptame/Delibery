package com.example.delivery_app_grupo_6.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivery_app_grupo_6.data.model.User
import com.example.delivery_app_grupo_6.validacion.Validators

@Composable
fun PantallaCrearPerfil(
    onBackClick: () -> Unit,
    onSaveClick: (User) -> Unit,
    user: User?,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    var name by remember { mutableStateOf(user?.name ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var phone by remember { mutableStateOf(user?.phone ?: "") }
    var address by remember { mutableStateOf(user?.address ?: "") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var addressError by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        nameError = if (name.isBlank()) "El nombre es requerido" else ""
        emailError = if (email.isBlank()) "El email es requerido"
        else if (!Validators.isValidEmail(email)) "Email inválido" else ""
        phoneError = if (phone.isBlank()) "El teléfono es requerido"
        else if (!Validators.isValidPhone(phone)) "Teléfono debe tener al menos 10 dígitos" else ""
        addressError = if (address.isBlank()) "La dirección es requerida" else ""

        return nameError.isEmpty() && emailError.isEmpty() && phoneError.isEmpty() && addressError.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("← Volver")
        }

        Text(
            text = if (user != null) "👤 Editar Perfil" else "👤 Crear Perfil",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                if (it.isNotBlank()) nameError = ""
            },
            label = { Text("Nombre completo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = nameError.isNotEmpty(),
            supportingText = {
                if (nameError.isNotEmpty()) Text(nameError)
            }
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (it.isNotBlank() && Validators.isValidEmail(it)) emailError = ""
            },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = emailError.isNotEmpty(),
            supportingText = {
                if (emailError.isNotEmpty()) Text(emailError)
            }
        )

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                if (it.isNotBlank() && Validators.isValidPhone(it)) phoneError = ""
            },
            label = { Text("Teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = phoneError.isNotEmpty(),
            supportingText = {
                if (phoneError.isNotEmpty()) Text(phoneError)
            }
        )

        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
                if (it.isNotBlank()) addressError = ""
            },
            label = { Text("Dirección de entrega") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = addressError.isNotEmpty(),
            supportingText = {
                if (addressError.isNotEmpty()) Text(addressError)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateForm()) {
                    val newUser = User(
                        id = user?.id ?: 0,
                        name = name,
                        email = email,
                        phone = phone,
                        address = address
                    )
                    onSaveClick(newUser)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (user != null) "💾 Actualizar Perfil" else "💾 Guardar Perfil")
        }
    }
}