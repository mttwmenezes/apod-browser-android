package com.github.mttwmenezes.apodbrowser.feature.bookmarks.model

import com.github.mttwmenezes.apodbrowser.data.model.Apod

data class BookmarksUiState(
    val isEmpty: Boolean = false,
    val isFailure: Boolean = false,
    val isSuccess: Boolean = false,
    val bookmarks: List<Apod> = emptyList()
)
