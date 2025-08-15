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

package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksFeedItem
import com.github.mttwmenezes.apodbrowser.feature.other.extension.capitalized
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class BookmarksFeedBuilder @Inject constructor() {

    fun build(apods: List<Apod>) = apods.map { feedItem(it) }

    private fun feedItem(apod: Apod) = BookmarksFeedItem.Apod(
        itemId = apod.date,
        imageUrl = if (apod.isImage) apod.url else apod.thumbnailUrl,
        title = apod.title,
        mediaType = apod.mediaType.capitalized()
    )
}
