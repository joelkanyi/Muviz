/*
 * Copyright 2024 Joel Kanyi.
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
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 36
    namespace = "com.kanyideveloper.muviz"

    defaultConfig {
        applicationId = "com.kanyideveloper.muviz"
        minSdk = 21
        targetSdk = compileSdk
        versionCode = 6
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$rootDir/app/schemas/")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            versionNameSuffix = " - debug-1"
            applicationIdSuffix = ".debug"
            buildConfigField("int", "PATCH_VERSION_CODE", "1")

            val apiKey: String = gradleLocalProperties(rootDir, providers).getProperty("API_KEY") ?: ""
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // PATCH_VERSION_CODE is always -1 for release builds.
            buildConfigField("int", "PATCH_VERSION_CODE", "-1")

            val apiKey: String = gradleLocalProperties(rootDir, providers).getProperty("API_KEY") ?: ""
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs(files("$rootDir/app/schemas")) // Room
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.appcompat)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.flowlayout)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Timber
    implementation(libs.jakewharton.timber)

    // RamCosta Navigation
    implementation(libs.compose.destination.core)
    ksp(libs.compose.destination.ksp)

    // Navigation animation
    implementation(libs.accompanist.navigation.animation)

    // Accompanist System UI controller
    implementation(libs.accompanist.systemuicontroller)

    // Insets
    implementation(libs.accompanist.insets)

    // Coil
    implementation(libs.coil.compose)

    // Timber
    implementation(libs.jakewharton.timber)

    // Paging Compose
    implementation(libs.androidx.paging.compose)

    // Swipe to refresh
    implementation(libs.accompanist.swiperefresh)

    // DataStore
    implementation(libs.datastore.preferences)
}