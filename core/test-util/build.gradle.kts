plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.test_util"
}

dependencies {
    implementation(project(":data"))
}