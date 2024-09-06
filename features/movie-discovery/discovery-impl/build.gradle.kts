plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.junit5)

    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))

android {
    namespace = "com.aditya.discovery.impl"
}

dependencies {
    implementation(project(":discovery-api"))
    implementation(project(":movie-detail-api"))
    implementation(project(":ui"))
    implementation(project(":data"))

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


    //test
    testImplementation(project(":test-util"))
}