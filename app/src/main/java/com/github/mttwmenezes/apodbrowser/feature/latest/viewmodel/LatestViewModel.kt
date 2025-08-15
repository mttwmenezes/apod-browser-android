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

package com.github.mttwmenezes.apodbrowser.feature.latest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.data.repository.ApodRepository
import com.github.mttwmenezes.apodbrowser.feature.latest.model.LatestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestViewModel @Inject constructor(
    private val repository: ApodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LatestUiState())
    val uiState get() = _uiState.asStateFlow()

    private var fetchLatestJob: Job? = null
    private var fetchApodJob: Job? = null

    init {
        fetchLatest()
    }

    fun fetchLatest() {
        fetchLatestJob?.cancel()
        fetchLatestJob = viewModelScope.launch {
            _uiState.value = loadingUiState
            repository.fetchLatest().also {
                _uiState.value = if (it.isNotEmpty()) successUiState(it) else failureUiState
            }
        }
    }

    private val loadingUiState get() = LatestUiState(isLoading = true)

    private val failureUiState get() = LatestUiState(isFailure = true)

    private fun successUiState(apods: List<Apod>) = LatestUiState(isSuccess = true, apods = apods)

    fun findApodBy(id: String) = uiState.value.apods.find { it.date == id }

    fun fetchFromDate(dateInMillis: Long, onComplete: (Apod?) -> Unit) {
        fetchApodJob?.cancel()
        fetchApodJob = viewModelScope.launch { onComplete(repository.fetchFromDate(dateInMillis)) }
    }

    fun fetchRandom(onComplete: (Apod?) -> Unit) {
        fetchApodJob?.cancel()
        fetchApodJob = viewModelScope.launch { onComplete(repository.fetchRandom()) }
    }
}
