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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemApodBinding
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemExploreHintBinding
import com.github.mttwmenezes.apodbrowser.feature.latest.model.LatestFeedItem

class LatestFeedAdapter(
    private val listener: Listener
) : ListAdapter<LatestFeedItem, LatestFeedViewHolder>(LatestFeedDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        R.layout.feed_item_apod -> {
            LatestFeedViewHolder.Apod(
                FeedItemApodBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        R.layout.feed_item_explore_hint -> {
            LatestFeedViewHolder.ExploreHint(
                FeedItemExploreHintBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        else -> throw IllegalArgumentException("Invalid viewType for adapter: $viewType")
    }

    override fun onBindViewHolder(holder: LatestFeedViewHolder, position: Int) {
        when (holder) {
            is LatestFeedViewHolder.Apod -> {
                holder.bind(
                    item = getItem(position) as LatestFeedItem.Apod,
                    onItemClickListener = { listener.onFeedItemClicked(it) },
                    onItemOptionsClickListener = { listener.onFeedItemOptionsClicked(it) }
                )
            }

            is LatestFeedViewHolder.ExploreHint -> {
                holder.bind { listener.onFeedItemExploreHintClicked() }
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).viewType

    interface Listener {
        fun onFeedItemClicked(id: String)

        fun onFeedItemOptionsClicked(id: String)

        fun onFeedItemExploreHintClicked()
    }
}

class LatestFeedDiffUtilItemCallback : DiffUtil.ItemCallback<LatestFeedItem>() {

    override fun areItemsTheSame(oldItem: LatestFeedItem, newItem: LatestFeedItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LatestFeedItem, newItem: LatestFeedItem) =
        oldItem == newItem
}
