package com.github.mttwmenezes.apodbrowser.feature.bookmarks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.data.repository.BookmarksRepository
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: BookmarksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookmarksUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        fetchBookmarks()
    }

    private fun fetchBookmarks() {
        viewModelScope.launch {
            repository.fetchAll()
                .catch { _uiState.value = failureState }
                .collect {
                    _uiState.value = if (it.isNotEmpty()) successState(it) else emptyState
                }
        }
    }

    private val failureState get() = BookmarksUiState(isFailure = true)

    private fun successState(bookmarks: List<Apod>) =
        BookmarksUiState(isSuccess = true, bookmarks = bookmarks.reversed())

    private val emptyState get() = BookmarksUiState(isEmpty = true)
}
