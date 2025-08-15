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
import com.github.mttwmenezes.apodbrowser.ApodBrowserApp
import com.github.mttwmenezes.apodbrowser.feature.home.view.HomeActivity
import com.github.mttwmenezes.apodbrowser.feature.other.delegate.HomeLayoutDelegate
import com.github.mttwmenezes.apodbrowser.feature.other.image.DeviceGallery
import com.github.mttwmenezes.apodbrowser.feature.other.image.ImageSharer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideHomeLayoutDelegate(@ActivityContext context: Context) =
        context as HomeActivity as HomeLayoutDelegate

    @Provides
    fun provideApplicationScope(@ApplicationContext context: Context) =
        (context as ApodBrowserApp).applicationScope

    @Provides
    fun provideDeviceGallery(
        @ActivityContext context: Context,
        applicationScope: CoroutineScope
    ) = DeviceGallery(context, applicationScope)

    @Provides
    fun provideImageSharer(
        @ActivityContext context: Context,
        applicationScope: CoroutineScope
    ) = ImageSharer(context, applicationScope)
}
