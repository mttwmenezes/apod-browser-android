package com.github.mttwmenezes.apodbrowser.feature.latest.model

import androidx.annotation.LayoutRes
import com.github.mttwmenezes.apodbrowser.R
import java.util.UUID

sealed class LatestFeedItem(
    val id: String,
    @LayoutRes val viewType: Int
) {
    data class Apod(
        val itemId: String,
        val imageUrl: String?,
        val title: String,
        val mediaType: String,
        val date: String,
    ) : LatestFeedItem(id = itemId, viewType = R.layout.feed_item_apod)

    data object ExploreHint : LatestFeedItem(
        id = UUID.randomUUID().toString(),
        viewType = R.layout.feed_item_explore_hint
    )
}
