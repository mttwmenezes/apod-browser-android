package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemBookmarkBinding
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksFeedItem

class BookmarksFeedAdapter(
    private val listener: Listener
) : ListAdapter<BookmarksFeedItem, BookmarksFeedViewHolder>(BookmarksFeedDiffUtilItemCallback()) {

    private var displayWidth = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarksFeedViewHolder.Apod {
        displayWidth = parent.context.resources.displayMetrics.widthPixels
        return BookmarksFeedViewHolder.Apod(
            FeedItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarksFeedViewHolder, position: Int) {
        holder.itemView.updateLayoutParams { width = displayWidth / COMPACT_SPAN_COUNT }
        if (holder is BookmarksFeedViewHolder.Apod) {
            holder.bind(
                item = getItem(position) as BookmarksFeedItem.Apod,
                onItemClickListener = { listener.onFeedItemClicked(it) }
            )
        }
    }

    interface Listener {
        fun onFeedItemClicked(id: String)
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
