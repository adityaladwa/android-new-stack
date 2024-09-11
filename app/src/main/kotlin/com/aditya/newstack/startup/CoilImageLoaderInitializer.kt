package com.aditya.newstack.startup

import android.content.Context
import androidx.startup.Initializer
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.aditya.logger.logger
import kotlinx.coroutines.Dispatchers

class CoilImageLoaderInitializer : Initializer<ImageLoader> {
    companion object {
        var imageLoader: ImageLoader? = null
    }

    override fun create(context: Context): ImageLoader {
        logger.d("CoilImageLoaderInitializer", "create")
        imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .maxSizeBytes(50L * 1024 * 1024) // 50MB cache
                    .directory(context.cacheDir.resolve("image_cache"))
                    .build()
            }
            .dispatcher(Dispatchers.IO.limitedParallelism(2, "coil-image-loader"))
            .logger(DebugLogger())
            .build()
        return imageLoader!!
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}