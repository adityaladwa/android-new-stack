package com.aditya.newstack.startup

import android.content.Context
import androidx.startup.Initializer
import coil.ImageLoader
import coil.util.DebugLogger

class CoilImageLoaderInitializer : Initializer<ImageLoader> {
    override fun create(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .logger(DebugLogger())
            .build()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}