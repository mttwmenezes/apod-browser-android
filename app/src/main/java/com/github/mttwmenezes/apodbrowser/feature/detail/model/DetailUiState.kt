package com.github.mttwmenezes.apodbrowser.feature.detail.model

import androidx.annotation.StringRes
import com.github.mttwmenezes.apodbrowser.R

data class DetailUiState(
    val isBookmarked: Boolean = false,
    val bookmarkMessage: DetailBookmarkMessage? = null
)

enum class DetailBookmarkMessage(@StringRes val resId: Int) {
    Added(R.string.message_bookmark_added),
    Removed(R.string.message_bookmark_removed),
    Error(R.string.message_unexpected_error_occurred)
}
