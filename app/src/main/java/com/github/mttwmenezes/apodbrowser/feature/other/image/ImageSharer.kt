/*
 * Copyright 2025 Matheus Menezes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mttwmenezes.apodbrowser.feature.other.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageSharer @Inject constructor(
    private val context: Context,
    private val externalScope: CoroutineScope
) {
    fun share(
        image: Bitmap,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) = with(context) {
        val path = File(cacheDir, "images")
        path.mkdirs()

        val file = File(path, "temp_${System.currentTimeMillis()}.png")

        externalScope.launch {
            try {
                FileOutputStream(file).use {
                    image.compress(Bitmap.CompressFormat.PNG, 100, it)
                    it.flush()
                    it.close()
                }
            } catch (e: Exception) {
                onFailure()
            }

            withContext(Dispatchers.Main.immediate) {
                FileProvider.getUriForFile(
                    context,
                    packageName.plus(".").plus("fileprovider"),
                    file
                )?.let {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        putExtra(Intent.EXTRA_STREAM, it)
                        setType("image/png")
                    }
                    startActivity(Intent.createChooser(intent, null))
                    onSuccess()
                } ?: run {
                    onFailure()
                }
            }
        }
        file.delete()
    }
}
