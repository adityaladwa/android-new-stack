import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.kapt)
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
        buildConfigField("String", "TMDB_BASE_URL", localProperties.getProperty("TMDB_BASE_URL").toString())
        buildConfigField("String", "TMDB_API_KEY", localProperties.getProperty("TMDB_API_KEY").toString())
    }
}

dependencies {
    implementation(project(":discovery-api"))

    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    //okhttp
    implementation(platform(libs.squareup.okhttp.bom))
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.logging.interceptor)
    implementation(libs.squareup.okio)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}