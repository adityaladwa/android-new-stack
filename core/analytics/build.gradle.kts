plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version libs.versions.kotlin

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.analytics"
}

dependencies {
    implementation(project(":logger"))

    implementation(libs.squareup.tape)

    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}