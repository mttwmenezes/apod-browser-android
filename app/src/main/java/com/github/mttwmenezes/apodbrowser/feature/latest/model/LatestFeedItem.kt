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

package com.github.mttwmenezes.apodbrowser.feature.latest.model

import androidx.annotation.LayoutRes
import com.github.mttwmenezes.apodbrowser.R
import java.util.UUID

sealed class LatestFeedItem(
    val id: String,
    @LayoutRes val viewType: Int
) {
    data class Apod(
        val itemId: String,
        val imageUrl: String?,
        val title: String,
        val mediaType: String,
        val date: String,
    ) : LatestFeedItem(id = itemId, viewType = R.layout.feed_item_apod)

    data object ExploreHint : LatestFeedItem(
        id = UUID.randomUUID().toString(),
        viewType = R.layout.feed_item_explore_hint
    )
}
