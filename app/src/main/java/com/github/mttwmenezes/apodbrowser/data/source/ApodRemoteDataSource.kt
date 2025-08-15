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

    suspend fun fetchFromDate(date: String) = withContext(dispatcher) {
        runCatching {
            service.fetchFromDate(date = date)
        }.fold(
            onSuccess = { it.body() },
            onFailure = { null }
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
