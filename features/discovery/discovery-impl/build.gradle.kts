plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.discovery.impl"
}

dependencies {
    implementation(project(":discovery-api"))
    implementation(project(":ui"))
    implementation(project(":data"))
}