package com.aditya.newstack

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.aditya.newstack.startup.CoilImageLoaderInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return CoilImageLoaderInitializer.imageLoader!!
    }
}