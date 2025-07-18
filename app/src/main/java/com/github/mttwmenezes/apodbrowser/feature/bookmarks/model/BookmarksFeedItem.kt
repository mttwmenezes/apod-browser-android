package com.github.mttwmenezes.apodbrowser.feature.bookmarks.model

import androidx.annotation.LayoutRes
import com.github.mttwmenezes.apodbrowser.R

sealed class BookmarksFeedItem(
    val id: String,
    @LayoutRes val viewType: Int
) {
    data class Apod(
        val itemId: String,
        val imageUrl: String?,
        val title: String
    ) : BookmarksFeedItem(id = itemId, viewType = R.layout.feed_item_bookmark)
}
