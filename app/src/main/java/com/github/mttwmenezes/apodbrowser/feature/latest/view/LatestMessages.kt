package com.github.mttwmenezes.apodbrowser.feature.latest.view

import android.view.View
import com.github.mttwmenezes.apodbrowser.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class LatestMessages @Inject constructor() {

    fun showUnexpectedErrorMessage(root: View, anchor: View) {
        Snackbar.make(root, R.string.message_unexpected_error_occurred, Snackbar.LENGTH_SHORT)
            .setAnchorView(anchor)
            .show()
    }
}
