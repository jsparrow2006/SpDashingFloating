import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "jsp.develop.floatingdashing"
    compileSdk = 34

    packaging {
        excludes += "META-INF/INDEX.LIST"
        excludes += "META-INF/INDEX.LIST.meta"
        excludes += "META-INF/manifest.mf"
        excludes += "META-INF/io.netty.versions.properties"
    }

    defaultConfig {
        applicationId = "jsp.develop.floatingdashing"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//    implementation("org.nanohttpd:nanohttpd:2.3.1")
    implementation ("io.ktor:ktor-server-core:2.3.1")
    implementation ("io.ktor:ktor-server-netty:2.3.1")
    implementation ("io.ktor:ktor-server-html-builder:2.3.1")
    implementation ("io.ktor:ktor-server-content-negotiation:2.3.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}