package com.aditya.newstack

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger

class CoilSetup {
    fun setup(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .logger(DebugLogger())
            .build()
    }
}