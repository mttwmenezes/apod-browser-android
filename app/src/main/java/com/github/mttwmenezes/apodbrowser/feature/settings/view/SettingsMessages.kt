package com.github.mttwmenezes.apodbrowser.feature.settings.view

import android.view.View
import com.github.mttwmenezes.apodbrowser.R
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SettingsMessages @Inject constructor() {

    fun showImageCacheClearedMessage(root: View) {
        Snackbar.make(root, R.string.message_image_cache_cleared, Snackbar.LENGTH_SHORT).show()
    }
}
