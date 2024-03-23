plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.dayforge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dayforge"
        minSdk = 29
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
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android
    implementation(libs.androidx.core.ktx)
    implementation(
        libs
            .androidx
            .lifecycle
            .runtime
            .ktx,
    )
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(
        libs
            .compose
            .ui
            .tooling
            .preview,
    )
    implementation(libs.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":data-local"))
    implementation(project(":domain"))
    implementation(project(":data-repository"))

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(
        platform(
            libs
                .compose
                .ui
                .test
                .bom,
        ),
    )
    androidTestImplementation(
        libs
            .compose
            .ui
            .test
            .junit4,
    )
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(
        libs
            .compose
            .ui
            .test
            .manifest,
    )

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(
        libs
            .androidx
            .hilt
            .navigation
            .compose,
    )

    implementation(libs.textflow)
    implementation(libs.androidx.core.splashscreen)
}