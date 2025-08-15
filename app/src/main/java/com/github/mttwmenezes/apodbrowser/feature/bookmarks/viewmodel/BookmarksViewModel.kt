/*
 * Copyright 2025 Matheus Menezes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import kotlinx.coroutines.flow.update
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

    fun fetchBookmarks() {
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

    fun findBookmarksBy(searchQuery: String) {
        viewModelScope.launch {
            repository.findByQuery(searchQuery)
                .catch { _uiState.value = failureState }
                .collect { bookmarks ->
                    _uiState.update {
                        if (bookmarks.isNotEmpty()) {
                            it.copy(isSearchEmpty = false, bookmarks = bookmarks.reversed())
                        } else {
                            it.copy(isSuccess = false, isSearchEmpty = true, bookmarks = emptyList())
                        }
                    }
                }
        }
    }

    fun findBookmarkBy(id: String) = uiState.value.bookmarks.find { it.date == id }
}
