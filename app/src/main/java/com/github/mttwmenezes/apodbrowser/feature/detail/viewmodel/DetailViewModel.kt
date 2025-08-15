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

package com.github.mttwmenezes.apodbrowser.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.data.repository.BookmarksRepository
import com.github.mttwmenezes.apodbrowser.feature.detail.model.DetailBookmarkMessage
import com.github.mttwmenezes.apodbrowser.feature.detail.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: BookmarksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState get() = _uiState.asStateFlow()

    fun determineBookmarkState(apod: Apod) {
        viewModelScope.launch {
            runCatching {
                repository.isBookmarked(apod)
            }.fold(
                onSuccess = { value ->
                    _uiState.update { it.copy(isBookmarked = value) }
                },
                onFailure = {
                    _uiState.update { it.copy(bookmarkMessage = DetailBookmarkMessage.Error) }
                }
            )
        }
    }

    fun addToBookmarks(apod: Apod) {
        viewModelScope.launch {
            runCatching {
                repository.add(apod)
            }.fold(
                onSuccess = {
                    _uiState.update { it.copy(bookmarkMessage = DetailBookmarkMessage.Added) }
                    determineBookmarkState(apod)
                },
                onFailure = {
                    _uiState.update { it.copy(bookmarkMessage = DetailBookmarkMessage.Error) }
                }
            )
        }
    }

    fun removeFromBookmarks(apod: Apod) {
        viewModelScope.launch {
            runCatching {
                repository.remove(apod)
            }.fold(
                onSuccess = {
                    _uiState.update { it.copy(bookmarkMessage = DetailBookmarkMessage.Removed) }
                    determineBookmarkState(apod)
                },
                onFailure = {
                    _uiState.update { it.copy(bookmarkMessage = DetailBookmarkMessage.Error) }
                }
            )
        }
    }

    fun messageShown() = _uiState.update { it.copy(bookmarkMessage = null) }
}
