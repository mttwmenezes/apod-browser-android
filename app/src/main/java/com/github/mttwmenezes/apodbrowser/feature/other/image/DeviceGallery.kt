package com.github.mttwmenezes.apodbrowser.feature.other.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeviceGallery @Inject constructor(
    private val context: Context,
    private val externalScope: CoroutineScope
) {
    fun add(image: Bitmap, filename: String) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$filename.png")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        }
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        val outputStream = resolver.openOutputStream(uri!!)

        externalScope.launch {
            outputStream?.use {
                image.compress(Bitmap.CompressFormat.PNG, 100, it)
                it.close()
            }
        }
    }
}
