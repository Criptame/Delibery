package com.example.delivery_app_grupo_6.ui.screen

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import android.graphics.Bitmap
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCamaraConQR(
    onBackClick: () -> Unit,
    onQRScannerClick: () -> Unit,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var currentTab by remember { mutableStateOf(0) } // 0 = Cámara, 1 = QR
    var capturedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var showPreview by remember { mutableStateOf(false) }

    // Estado y lanzador para permisos nativos
    var hasCameraPermission by remember { mutableStateOf(false) }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    // Solicitar permiso al iniciar
    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (currentTab == 0) "Cámara" else "Escáner QR",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Selector de pestañas
            TabRow(
                selectedTabIndex = currentTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    text = { Text("Cámara") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cámara"
                        )
                    }
                )
                Tab(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    text = { Text("Escáner QR") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "Escáner QR"
                        )
                    }
                )
            }

            // Contenido según la pestaña seleccionada
            when (currentTab) {
                0 -> ContenidoCamaraReal(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    hasCameraPermission = hasCameraPermission,
                    capturedImage = capturedImage,
                    showPreview = showPreview,
                    onImageCaptured = { bitmap ->
                        capturedImage = bitmap
                        showPreview = true
                    },
                    onRetakePhoto = {
                        capturedImage = null
                        showPreview = false
                    },
                    onRequestPermission = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                )
                1 -> ContenidoQRSimplificado(onQRScannerClick)
            }
        }
    }
}

@Composable
private fun ContenidoCamaraReal(
    context: Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    hasCameraPermission: Boolean,
    capturedImage: ImageBitmap?,
    showPreview: Boolean,
    onImageCaptured: (ImageBitmap) -> Unit,
    onRetakePhoto: () -> Unit,
    onRequestPermission: () -> Unit
) {
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    if (!hasCameraPermission) {
        // Pantalla de solicitud de permisos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Cámara",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Permiso de Cámara Requerido",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Necesitamos acceso a tu cámara para tomar fotos de productos",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = onRequestPermission
            ) {
                Text("Solicitar Permiso")
            }
        }
    } else if (showPreview && capturedImage != null) {
        // Vista previa de la foto capturada
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    bitmap = capturedImage,
                    contentDescription = "Foto capturada",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onRetakePhoto,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Volver a Tomar")
                }

                Button(
                    onClick = {
                        // Aquí podrías guardar la foto o procesarla
                        onRetakePhoto()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Usar Foto")
                }
            }
        }
    } else {
        // Vista de la cámara en vivo
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Vista previa de la cámara
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val executor = ContextCompat.getMainExecutor(context)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                    cameraProviderFuture.addListener({
                        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                        val preview = Preview.Builder()
                            .build()
                            .also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                        imageCapture = ImageCapture.Builder()
                            .build()

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageCapture
                            )
                        } catch (exc: Exception) {
                            Log.e("Camera", "Use case binding failed", exc)
                        }
                    }, executor)

                    previewView
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // Controles de la cámara
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Controles de Cámara",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = {
                            // Capturar foto
                            val imageCapture = imageCapture ?: return@Button

                            val executor = ContextCompat.getMainExecutor(context)

                            imageCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    super.onCaptureSuccess(image)

                                    // Convertir ImageProxy a Bitmap
                                    val bitmap = image.toBitmap()
                                    val imageBitmap = bitmap.asImageBitmap()

                                    onImageCaptured(imageBitmap)
                                    image.close()
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Log.e("Camera", "Error capturando foto", exception)
                                }
                            })
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Tomar Foto",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tomar Foto")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• Enfoca en el producto que deseas fotografiar",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "• Mantén el dispositivo estable",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "• Usa buena iluminación",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun ContenidoQRSimplificado(
    onQRScannerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "QR Scanner",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Escáner de Códigos QR",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Para un escáner QR completo con CameraX, necesitaríamos implementar ML Kit o ZXing",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = onQRScannerClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Simular Escaneo QR")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Próximamente:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text("• Escáner QR con CameraX + ML Kit")
                Text("• Detección automática de códigos")
                Text("• Procesamiento en tiempo real")
            }
        }
    }
}

// Función de extensión para convertir ImageProxy a Bitmap
private fun ImageProxy.toBitmap(): Bitmap {
    val planeProxy = planes[0]
    val buffer = planeProxy.buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}