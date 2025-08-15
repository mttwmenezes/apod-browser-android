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

package com.github.mttwmenezes.apodbrowser.infrastructure.storage

import java.util.Locale
import kotlin.math.pow

object DirectoryUtils {

    fun sizeFormatted(bytes: Long) = when {
        bytes == 0L -> "0 KB"
        bytes > MEGABYTE -> "%.1f MB".format(Locale.US, bytes / MEGABYTE)
        bytes > GIGABYTE -> "%.1f GB".format(Locale.US, bytes / GIGABYTE)
        else -> "%.1f KB".format(Locale.US, bytes / KILOBYTE)
    }

    private const val KILOBYTE = 1024.0
    private val MEGABYTE = KILOBYTE.pow(2.0)
    private val GIGABYTE = KILOBYTE.pow(3.0)
}
