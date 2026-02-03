plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.github.mttwmenezes.apodbrowser"
    buildToolsVersion = "36.0.0"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.github.mttwmenezes.apodbrowser"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "stable-1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            manifestPlaceholders["fileProviderAuthority"] = buildString {
                append("com.github.mttwmenezes.apodbrowser")
                append(".debug")
                append(".fileprovider")
            }
        }
        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            manifestPlaceholders["fileProviderAuthority"] = buildString {
                append("com.github.mttwmenezes.apodbrowser")
                append(".staging")
                append(".fileprovider")
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders["fileProviderAuthority"] = buildString {
                append("com.github.mttwmenezes.apodbrowser")
                append(".fileprovider")
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Core testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)

    // Material components
    implementation(libs.material)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.gif)

    // Kotlin serialization
    implementation(libs.kotlin.serialization)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.kotlin.serialization)

    // SwipeRefreshLayout
    implementation(libs.androidx.swiperefreshlayout)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // PhotoView
    implementation(libs.photoview)

    // Preference
    implementation(libs.androidx.preference)
}
