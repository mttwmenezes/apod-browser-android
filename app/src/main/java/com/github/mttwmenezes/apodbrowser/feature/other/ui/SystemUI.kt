package com.github.mttwmenezes.apodbrowser.feature.other.ui

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SystemUI @Inject constructor(@ApplicationContext private val context: Context) {

    val statusBarHeight: Int
        get() = systemUiDimension("status_bar_height")

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun systemUiDimension(name: String): Int {
        val resId = context.resources.getIdentifier(name, "dimen", "android")
        return if (resId > 0) context.resources.getDimensionPixelSize(resId) else 0
    }

    val navigationBarHeight: Int
        get() = systemUiDimension("navigation_bar_height")
}
