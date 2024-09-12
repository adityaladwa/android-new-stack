import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.data"

    buildFeatures {
        buildConfig = true
    }

    // Load the local.properties file
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    defaultConfig {
        buildConfigField(
            "String",
            "TMDB_BASE_URL",
            localProperties.getProperty("TMDB_BASE_URL") ?: "\"default\""
        )
        buildConfigField(
            "String",
            "TMDB_IMAGE_BASE_URL",
            localProperties.getProperty("TMDB_IMAGE_BASE_URL") ?: "\"default\""
        )
        buildConfigField(
            "String",
            "TMDB_API_KEY",
            localProperties.getProperty("TMDB_API_KEY") ?: "\"default\""
        )
    }
}

dependencies {
    implementation(project(":discovery-api"))
    implementation(project(":movie-detail-api"))

    //coroutines
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)


    //okhttp
    api(platform(libs.squareup.okhttp.bom))
    api(libs.squareup.okhttp)
    api(libs.squareup.okhttp.logging.interceptor)
    api(libs.squareup.okio)

    //retrofit
    api(libs.squareup.retrofit)
    api(libs.squareup.retrofit.converter.kotlinx.serialization)

    //serialization
    api(libs.kotlinx.serialization.json)


    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}