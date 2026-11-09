plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.kotlin.compose.compiler)
}

android {
    namespace = "io.github.joelkanyi.search"
    compileSdk = 36

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(projects.core)
    implementation(projects.core.model)
    implementation(projects.feature.genre)
}

ksp {
    // used on some of the generated code, including default package name
    arg("compose-destinations.moduleName", "search")
    // and if you want to generate mermaid graph files for this module's graphs:
    // (ideally use the same path for all modules, so that navigation in the html works well)
    arg("compose-destinations.htmlMermaidGraph", "$rootDir//navigation-docs")
    arg("compose-destinations.mermaidGraph", "$rootDir/navigation-docs")
}