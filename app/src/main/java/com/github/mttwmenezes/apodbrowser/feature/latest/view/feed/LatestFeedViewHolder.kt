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

package com.github.mttwmenezes.apodbrowser.feature.latest.view.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemApodBinding
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemExploreHintBinding
import com.github.mttwmenezes.apodbrowser.feature.latest.model.LatestFeedItem
import com.github.mttwmenezes.apodbrowser.feature.other.extension.setClickableSpan
import com.github.mttwmenezes.apodbrowser.feature.other.extension.show

sealed class LatestFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Apod(private val binding: FeedItemApodBinding) : LatestFeedViewHolder(binding.root) {

        fun bind(
            item: LatestFeedItem.Apod,
            onItemClickListener: (String) -> Unit,
            onItemOptionsClickListener: (String) -> Unit
        ) = with(binding) {
            loadApodImage(item.imageUrl)
            titleLabel.text = item.title
            mediaTypeLabel.text = item.mediaType
            dateLabel.text = item.date

            rippleOverlay.setOnClickListener { onItemClickListener(item.itemId) }
            optionsButton.setOnClickListener { onItemOptionsClickListener(item.itemId) }
        }

        private fun loadApodImage(imageUrl: String?) {
            binding.apodImage.load(imageUrl) {
                crossfade(true)
                listener(
                    onCancel = { showErrorPlaceholder() },
                    onError = { _, _ -> showErrorPlaceholder() }
                )
            }
        }

        private fun showErrorPlaceholder() = with(binding) {
            apodImage.visibility = View.INVISIBLE
            errorPlaceholder.show()
        }
    }

    class ExploreHint(
        private val binding: FeedItemExploreHintBinding
    ) : LatestFeedViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(onExploreHintClickListener: () -> Unit) {
            binding.exploreHintLabel.setClickableSpan(
                text = context.getString(R.string.latest_feed_explore_hint),
                clickableText = context.getString(R.string.latest_feed_explore_hint_clickable_text),
                onSpanClickListener = onExploreHintClickListener
            )
        }
    }
}
