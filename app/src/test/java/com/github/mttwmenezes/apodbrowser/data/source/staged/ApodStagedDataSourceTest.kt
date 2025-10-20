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

package com.github.mttwmenezes.apodbrowser.data.source.staged

import com.github.mttwmenezes.apodbrowser.fixture.network.response.rawApodResponse
import com.github.mttwmenezes.apodbrowser.infrastructure.network.response.ApodResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ApodStagedDataSourceTest {

    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var source: ApodStagedDataSource

    @Test
    fun `fetch should return success when valid raw JSON is provided`() {
        val expected = json.decodeFromString<List<ApodResponse>>(rawApodResponse)
        source = ApodStagedDataSource(json, rawJson = rawApodResponse)

        val actual = source.fetch()

        assertTrue(actual.isSuccess)
        assertNotNull(actual.getOrNull())
        assertEquals(expected, actual.getOrNull())
    }

    @Test
    fun `fetch should return failure when malformed raw JSON is provided`() {
        val rawMalformedJson = "[{\\\"title\\\": \\\"test\\\""
        source = ApodStagedDataSource(json, rawJson = rawMalformedJson)

        val actual = source.fetch()

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is SerializationException)
    }

    @Test
    fun `fetch should return success with empty list when empty JSON dictionary is provided`() {
        source = ApodStagedDataSource(json, rawJson = "[]")

        val actual = source.fetch()

        assertTrue(actual.isSuccess)
        assertNotNull(actual.getOrNull())
        assertEquals(actual.getOrNull(), emptyList<ApodResponse>())
    }

    @Test
    fun `fetch should return failure when raw JSON with missing fields is provided`() {
        source = ApodStagedDataSource(json, rawJson = "{}")

        val actual = source.fetch()

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is SerializationException)
    }
}
