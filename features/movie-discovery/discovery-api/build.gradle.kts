plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.discovery.api"
}

dependencies {
    //serialization
    implementation(libs.kotlinx.serialization.json)

}