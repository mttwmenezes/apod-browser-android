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

package com.github.mttwmenezes.apodbrowser.data.repository

import com.github.mttwmenezes.apodbrowser.BuildType
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.data.model.toApod
import com.github.mttwmenezes.apodbrowser.data.source.ApodRemoteDataSource
import com.github.mttwmenezes.apodbrowser.data.source.staged.ApodStagedDataSource
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApodRepository @Inject constructor(
    private val source: ApodRemoteDataSource,
    private val stagedSource: ApodStagedDataSource,
    private val dateFormatter: DateFormatter,
    private val dispatcher: CoroutineDispatcher,
    private val buildType: BuildType
) {
    suspend fun fetchLatest() = withContext(dispatcher) {
        if (buildType == BuildType.STAGING) {
            stagedSource.fetch()
                .getOrElse { emptyList() }
                .map { it.toApod() }
                .also { assignDescendingDates(it) }
        } else {
            source.fetchFromDateRange(startDate = pastWeek).map { it.toApod() }.reversed()
        }
    }

    private fun assignDescendingDates(apods: List<Apod>): List<Apod> {
        val today = Date.now()
        return apods.mapIndexed { index, apod ->
            apod.copy(
                date = dateFormatter.format(
                    date = today.minusDays(index.toLong()),
                    style = DateFormatter.Style.Iso
                )
            )
        }
    }

    private val pastWeek
        get() = dateFormatter.format(Date.now().minusWeeks(1L), DateFormatter.Style.Iso)

    suspend fun fetchFromDate(dateInMillis: Long) = withContext(dispatcher) {
        val formattedDate = dateFormatter.format(Date.parse(dateInMillis), DateFormatter.Style.Iso)
        source.fetchFromDate(formattedDate)?.toApod()
    }

    suspend fun fetchRandom() = withContext(dispatcher) { source.fetchRandom()?.toApod() }
}
