package com.github.mttwmenezes.apodbrowser.feature.detail.view

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailMessage @Inject constructor() {

    fun show(@StringRes resId: Int, root: View, anchor: View) {
        Snackbar.make(root, resId, Snackbar.LENGTH_SHORT)
            .setAnchorView(anchor)
            .show()
    }
}
