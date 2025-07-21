package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
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
            image.load(item.imageUrl) {
                listener(
                    onStart = { onImageLoadStarted() },
                    onSuccess = { _, _ -> onImageLoadSuccess() }
                )
            }
            titleLabel.text = item.title
            root.setOnClickListener { onItemClickListener(item.itemId) }
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
                dimensionRatio = null
            }
        }
    }
}
