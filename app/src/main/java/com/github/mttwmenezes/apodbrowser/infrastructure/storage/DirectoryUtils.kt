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
