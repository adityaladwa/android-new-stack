plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.junit5)

    alias(libs.plugins.ksp)
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
    implementation(project(":analytics"))

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    testImplementation(project(":testing"))


    //test
}