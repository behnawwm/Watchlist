package ir.behnawwm.watchlist.core.utils.extension

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
import ir.behnawwm.watchlist.R

/**
 * A class that holds the singleton [ImageLoader] instance.
 *
 * - To get the singleton [ImageLoader] use [Context.imageLoader] (preferred) or [imageLoader].
 */
object CoilUtils {

    private var imageLoader: ImageLoader? = null

    /**
     * Get the singleton [ImageLoader].
     */
    @JvmStatic
    fun imageLoader(context: Context) = imageLoader ?: newImageLoader(context)

    /**
     * Set the singleton [ImageLoader].
     * Prefer using `setImageLoader(ImageLoaderFactory)` to create the [ImageLoader] lazily.
     */
    @JvmStatic
    @Synchronized
    fun setImageLoader(imageLoader: ImageLoader) {
        this.imageLoader = imageLoader
    }


    /** Create and set the new singleton [ImageLoader]. */
    @OptIn(ExperimentalCoilApi::class)
    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        // Check again in case imageLoader was just set.
        imageLoader?.let { return it }

        // Create a new ImageLoader.
        val newImageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .placeholder(R.color.shimmerColor)
            .error(R.drawable.error_photo_placeholder)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
        imageLoader = newImageLoader
        return newImageLoader
    }


}