package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.github.mttwmenezes.apodbrowser.databinding.FeedItemBookmarkBinding
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.model.BookmarksFeedItem

sealed class BookmarksFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Apod(
        private val binding: FeedItemBookmarkBinding
    ) : BookmarksFeedViewHolder(binding.root) {

        fun bind(
            item: BookmarksFeedItem.Apod,
            onItemClickListener: (String) -> Unit
        ) = with(binding) {
            image.load(item.imageUrl)
            titleLabel.text = item.title
            root.setOnClickListener { onItemClickListener(item.itemId) }
        }
    }
}
