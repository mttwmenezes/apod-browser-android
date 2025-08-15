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

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemBookmarkBinding
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksFeedItem

sealed class BookmarksFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Apod(
        private val binding: FeedItemBookmarkBinding
    ) : BookmarksFeedViewHolder(binding.root) {

        fun bind(
            item: BookmarksFeedItem.Apod,
            onItemClickListener: (String) -> Unit,
            onItemLongClickListener: (String) -> Unit
        ) = with(binding) {
            image.load(item.imageUrl) {
                crossfade(true)
                listener(
                    onStart = { onImageLoadStarted() },
                    onSuccess = { _, _ -> onImageLoadSuccess() }
                )
            }
            titleLabel.text = item.title
            mediaTypeLabel.text = item.mediaType
            rippleOverlay.setOnClickListener { onItemClickListener(item.itemId) }
            rippleOverlay.setOnLongClickListener {
                onItemLongClickListener(item.itemId)
                true
            }
        }

        private fun onImageLoadStarted() = with(binding.image) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                dimensionRatio = "16:9"
            }
        }

        private fun onImageLoadSuccess() = with(binding.image) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                adjustViewBounds = true
                dimensionRatio = null
            }
        }
    }
}
