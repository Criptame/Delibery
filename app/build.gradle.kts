plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.48.1"
}

android {
    namespace = "com.example.delivery_app_grupo_6"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.delivery_app_grupo_6"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {
    // ✅ CORE ANDROID
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // ✅ NAVEGACIÓN Y UI COMPOSE
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // ✅ CÁMARA Y ESCÁNER QR
    implementation("com.google.zxing:core:3.5.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // ✅ BASE DE DATOS ROOM
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // ✅ CORRUTINAS Y LIFECYCLE
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // ✅ CONEXIÓN API REST
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // ✅ INYECCIÓN DE DEPENDENCIAS HILT
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-compiler:2.48.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // ✅ MANEJO DE IMÁGENES
    implementation("io.coil-kt:coil-compose:2.5.0")

    // ✅ PAGINACIÓN
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // ✅ DATASTORE
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // =============================================
    // ✅ TESTING - ORGANIZADO Y SIN DUPLICADOS
    // =============================================

    // 🔬 TEST UNITARIOS (test/)
    testImplementation("junit:junit:4.13.2") // Para compatibilidad
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2") // JUnit 5 moderno

    // Kotest - Framework moderno de testing
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.kotest:kotest-property:5.6.2")

    // Mocking
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.mockk:mockk-agent-jvm:1.13.8")

    // Testing Retrofit/APIs
    testImplementation("com.squareup.retrofit2:retrofit-mock:2.9.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // Corrutinas testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Android Architecture Components testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // 📱 TEST ANDROID/UI (androidTest/)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose UI Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0")
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Mocking para UI tests
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // 🐛 DEBUG IMPLEMENTATIONS
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.0")
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// ✅ CONFIGURACIÓN JUNIT 5 PARA KOTEST
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}