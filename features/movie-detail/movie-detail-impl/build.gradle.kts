plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

// Apply the Groovy script from the root directory
apply(from = rootProject.file("gradle/base-library.gradle"))


android {
    namespace = "com.aditya.movie_detail.impl"
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":data"))
    api(project(":movie-detail-api"))

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //image loading
    implementation(libs.coil)
    implementation(libs.coil.compose)
}