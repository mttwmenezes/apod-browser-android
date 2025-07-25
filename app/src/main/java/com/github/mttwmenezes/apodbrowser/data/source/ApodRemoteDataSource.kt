package com.github.mttwmenezes.apodbrowser.data.source

import com.github.mttwmenezes.apodbrowser.infrastructure.network.service.ApodService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApodRemoteDataSource @Inject constructor(
    private val service: ApodService,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchFromDateRange(startDate: String) = withContext(dispatcher) {
        runCatching {
            service.fetchFromDateRange(startDate = startDate)
        }.fold(
            onSuccess = { it.body().orEmpty() },
            onFailure = { emptyList() }
        )
    }

    suspend fun fetchRandom() = withContext(dispatcher) {
        runCatching {
            service.fetchRandom()
        }.fold(
            onSuccess = { it.body()?.first() },
            onFailure = { null }
        )
    }
}
