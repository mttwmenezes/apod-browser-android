package com.github.mttwmenezes.apodbrowser.data.repository

import com.github.mttwmenezes.apodbrowser.data.model.toApod
import com.github.mttwmenezes.apodbrowser.data.source.ApodRemoteDataSource
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApodRepository @Inject constructor(
    private val source: ApodRemoteDataSource,
    private val dateFormatter: DateFormatter,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchLatest() = withContext(dispatcher) {
        source.fetchFromDateRange(startDate = pastWeek).map { it.toApod() }.reversed()
    }

    private val pastWeek
        get() = dateFormatter.format(Date.now().minusWeeks(1L), DateFormatter.Style.Iso)

    suspend fun fetchRandom() = withContext(dispatcher) { source.fetchRandom()?.toApod() }
}
