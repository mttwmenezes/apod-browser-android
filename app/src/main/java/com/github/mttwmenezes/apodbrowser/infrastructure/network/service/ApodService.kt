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

package com.github.mttwmenezes.apodbrowser.infrastructure.network.service

import com.github.mttwmenezes.apodbrowser.infrastructure.network.response.ApodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodService {

    @GET(ENDPOINT)
    suspend fun fetchFromDateRange(
        @Query("api_key") key: String = API_KEY,
        @Query("start_date") startDate: String,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<List<ApodResponse>>

    @GET(ENDPOINT)
    suspend fun fetchFromDate(
        @Query("api_key") key: String = API_KEY,
        @Query("date") date: String,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<ApodResponse>

    @GET(ENDPOINT)
    suspend fun fetchRandom(
        @Query("api_key") key: String = API_KEY,
        @Query("count") count: Int = 1,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<List<ApodResponse>>

    companion object {
        const val BASE_URL = "https://api.nasa.gov/planetary/"
        const val ENDPOINT = "apod"
        const val API_KEY = "DEMO_KEY"
    }
}
