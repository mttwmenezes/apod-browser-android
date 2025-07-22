package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksFeedItem
import com.github.mttwmenezes.apodbrowser.feature.other.extension.capitalized
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class BookmarksFeedBuilder @Inject constructor() {

    fun build(apods: List<Apod>) = apods.map { feedItem(it) }

    private fun feedItem(apod: Apod) = BookmarksFeedItem.Apod(
        itemId = apod.date,
        imageUrl = if (apod.isImage) apod.url else apod.thumbnailUrl,
        title = apod.title,
        mediaType = apod.mediaType.capitalized()
    )
}
