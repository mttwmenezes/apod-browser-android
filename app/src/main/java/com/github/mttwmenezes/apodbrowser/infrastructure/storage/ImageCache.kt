package com.github.mttwmenezes.apodbrowser.infrastructure.storage

import android.content.Context
import coil3.imageLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImageCache @Inject constructor(@ApplicationContext private val context: Context) {

    val size: Long
        get() = context.imageLoader.diskCache?.size ?: 0L

    fun clear() {
        context.imageLoader.diskCache?.clear()
    }
}
