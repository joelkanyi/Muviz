import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

/*
 * Copyright 2025 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.kotlin.compose.compiler)
}

android {
    namespace = "io.github.joelkanyi.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val apiKey: String =
            gradleLocalProperties(rootDir, providers).getProperty("API_KEY") ?: ""
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
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
        buildConfig = true
    }
}

dependencies {
    // 🧱 Core Android + Kotlin
    implementation(libs.androidx.core.ktx)
    implementation(libs.jetbrains.coroutines.android)
    implementation(libs.androidx.appcompat)

    // 🎨 Jetpack Compose (UI)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.paging.compose)
    debugImplementation(libs.androidx.ui.tooling)

    // 🧭 Navigation
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    // ⚙️ Lifecycle & Architecture Components (MVVM)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // 💾 Data Storage & Persistence
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // 🌐 Networking (API)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.gson)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.interceptor)

    // 🧩 Dependency Injection (DI)
    implementation(libs.google.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.google.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)

    // 🖼️ Image Loading
    implementation(libs.coil.compose)

    // 🪵 Logging
    implementation(libs.timber)

    // 🧪 Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(projects.core.model)
}