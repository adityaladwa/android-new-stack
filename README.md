# Android sample project using the new stack

Architecture will follow most of the [Google recommended architecture](https://developer.android.com/topic/architecture/recommendations)


* Use coroutines or flows for async operations
* okhttp, retrofit for networking
* kotlinx.serialization for json serialization
* Hilt for dependency injection
* Jetpack compose for UI
* Use DataStore instead of shared-pref
* [Multi module app using api impl pattern](https://speakerdeck.com/vrallev/android-at-scale-at-square)
* Kotlin script instead of groovy
  * Use toml for versioning libraries
* Coil for image loading
