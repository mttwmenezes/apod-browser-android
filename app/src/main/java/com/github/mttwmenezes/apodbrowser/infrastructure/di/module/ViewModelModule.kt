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

package com.github.mttwmenezes.apodbrowser.infrastructure.di.module

import android.content.Context
import com.github.mttwmenezes.apodbrowser.BuildConfig
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.data.source.staged.ApodStagedDataSource
import com.github.mttwmenezes.apodbrowser.infrastructure.network.service.ApodService
import com.github.mttwmenezes.apodbrowser.infrastructure.util.readRawResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    fun provideApodService(retrofit: Retrofit): ApodService = retrofit.create()

    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    fun provideApodStagedDataSource(
        @ApplicationContext context: Context,
        json: Json
    ) = ApodStagedDataSource(
        decoder = json,
        rawJson = readRawResource(context, R.raw.apod_response_200)
    )
}
