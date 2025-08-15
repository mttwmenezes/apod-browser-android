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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemBookmarkBinding
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksFeedItem

class BookmarksFeedAdapter(
    private val listener: Listener
) : ListAdapter<BookmarksFeedItem, BookmarksFeedViewHolder>(BookmarksFeedDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksFeedViewHolder {
        val binding = FeedItemBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.updateLayoutParams<RecyclerView.LayoutParams> {
            width = parent.context.resources.displayMetrics.widthPixels / COMPACT_SPAN_COUNT
        }
        return BookmarksFeedViewHolder.Apod(binding)
    }

    override fun onBindViewHolder(holder: BookmarksFeedViewHolder, position: Int) {
        if (holder is BookmarksFeedViewHolder.Apod) {
            holder.bind(
                item = getItem(position) as BookmarksFeedItem.Apod,
                onItemClickListener = { listener.onFeedItemClicked(it) },
                onItemLongClickListener = { listener.onFeedItemLongClicked(it) }
            )
        }
    }

    interface Listener {
        fun onFeedItemClicked(id: String)

        fun onFeedItemLongClicked(id: String)
    }

    companion object {
        private const val COMPACT_SPAN_COUNT = 2
    }
}

class BookmarksFeedDiffUtilItemCallback : DiffUtil.ItemCallback<BookmarksFeedItem>() {

    override fun areItemsTheSame(oldItem: BookmarksFeedItem, newItem: BookmarksFeedItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BookmarksFeedItem, newItem: BookmarksFeedItem) =
        oldItem == newItem
}
