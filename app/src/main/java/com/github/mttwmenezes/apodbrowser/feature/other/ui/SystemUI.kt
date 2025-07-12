package com.github.mttwmenezes.apodbrowser.feature.other.ui

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SystemUI @Inject constructor(@ApplicationContext private val context: Context) {

    val navigationBarHeight: Int
        @SuppressLint("InternalInsetResource", "DiscouragedApi")
        get() {
            val resId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resId > 0) context.resources.getDimensionPixelSize(resId) else 0
        }
}
