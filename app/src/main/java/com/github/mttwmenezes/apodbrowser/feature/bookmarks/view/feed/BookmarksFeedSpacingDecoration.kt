package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.feature.other.ui.SystemUI
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BookmarksFeedSpacingDecoration @Inject constructor(
    @ApplicationContext private val context: Context,
    private val systemUI: SystemUI
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val position = parent.getChildAdapterPosition(view)
        val spanIndex = layoutParams.spanIndex

        if (spanIndex == 0) {
            outRect.left = marginSmall
            outRect.right = marginSmall / 2
        } else {
            outRect.left = marginSmall / 2
            outRect.right = marginSmall
        }

        outRect.top = if (position == 0 || position == 1) marginSmall else 0
        outRect.bottom = if (isLastItem(position, state)) {
            systemUI.navigationBarHeight + marginRegular
        } else {
            marginSmall
        }
    }

    private val marginSmall
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_small)

    private fun isLastItem(position: Int, state: State) = position == state.itemCount - 1

    private val marginRegular
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_regular)
}
