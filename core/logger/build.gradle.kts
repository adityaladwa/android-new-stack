
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.logger"
}

dependencies {

    //coroutines
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
}