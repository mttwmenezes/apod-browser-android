package com.github.mttwmenezes.apodbrowser.feature.image.view

import android.view.View
import com.github.mttwmenezes.apodbrowser.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ImageDetailMessages @Inject constructor() {

    fun showImageUnavailableMessage(root: View, anchor: View) {
        Snackbar.make(root, R.string.message_image_unavailable, Snackbar.LENGTH_SHORT)
            .setAnchorView(anchor)
            .show()
    }
}
