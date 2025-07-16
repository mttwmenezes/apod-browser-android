package com.github.mttwmenezes.apodbrowser.feature.latest.view.feed

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.feature.other.ui.SystemUI
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LatestFeedSpacingDecoration @Inject constructor(
    @ApplicationContext private val context: Context,
    private val systemUI: SystemUI
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val position = parent.getChildAdapterPosition(view)
        outRect.top = if (position == 0) marginSmall else 0
        outRect.bottom = if (isLastItem(position, state)) {
            systemUI.navigationBarHeight + marginRegular
        } else {
            marginRegular
        }
    }

    private val marginSmall
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_small)

    private val marginRegular
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_regular)

    private fun isLastItem(position: Int, state: State) = position == state.itemCount - 1
}
