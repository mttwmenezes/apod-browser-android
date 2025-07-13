package com.github.mttwmenezes.apodbrowser.feature.latest.model

import com.github.mttwmenezes.apodbrowser.data.model.Apod

data class LatestUiState(
    val isLoading: Boolean = false,
    val isFailure: Boolean = false,
    val isSuccess: Boolean = false,
    val apods: List<Apod> = emptyList()
)
