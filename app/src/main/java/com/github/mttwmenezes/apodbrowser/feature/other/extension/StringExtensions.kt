package com.github.mttwmenezes.apodbrowser.feature.other.extension

import java.util.Locale

fun String.capitalized() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
