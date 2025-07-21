package com.github.mttwmenezes.apodbrowser.feature.bookmarks.model

sealed class BookmarksFeedItem(val id: String) {

    data class Apod(
        val itemId: String,
        val imageUrl: String?,
        val title: String,
        val mediaType: String
    ) : BookmarksFeedItem(id = itemId)
}
